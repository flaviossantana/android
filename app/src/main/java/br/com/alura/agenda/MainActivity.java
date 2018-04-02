package br.com.alura.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import br.com.alura.agenda.adapter.AlunoAdapter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.event.AtualizarListaAlunoEvent;
import br.com.alura.agenda.modelo.Aluno;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_PHONE = 1;
    public static final int REQUEST_SMS = 2;
    private final br.com.alura.agenda.sync.AlunoSync alunoSync = new br.com.alura.agenda.sync.AlunoSync(this);

    private ListView listaAlunosView;
    private SwipeRefreshLayout swipe;

    private EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaAlunosView = findViewById(R.id.lista_alunos);
        swipe = findViewById(R.id.lista_aluno_swipe);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                alunoSync.buscarTodos();
                alunoSync.sincronizaInternos();
            }
        });


        eventBus = EventBus.getDefault();


        listaAlunosView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = getAlunoPosition(position);

                Intent irCadastro = new Intent(MainActivity.this, FormularioActivity.class);
                irCadastro.putExtra("aluno", aluno);
                startActivity(irCadastro);

                Toast.makeText(MainActivity.this, aluno.getNome() +  " clicado com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });

        Button incluirAlunobtn = findViewById(R.id.lista_incluir_aluno);
        incluirAlunobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent irCadastro = new Intent(MainActivity.this, FormularioActivity.class);
                startActivity(irCadastro);
            }
        });

        registerForContextMenu(listaAlunosView);

        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            String[] permissoes = {Manifest.permission.RECEIVE_SMS};
            ActivityCompat.requestPermissions(MainActivity.this, permissoes, REQUEST_SMS);
        }

        alunoSync.buscarTodos();
        alunoSync.sincronizaInternos();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void atualizarListaAlunoEvent(AtualizarListaAlunoEvent event){
        if(swipe.isRefreshing()){
            swipe.setRefreshing(false);
        }
        buscarAlunos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventBus.register(this);
        buscarAlunos();
    }

    @Override
    protected void onPause() {
        super.onPause();
        eventBus.unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lista_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_evniar_notas:
               new EnviarAlunoTask(this).execute();
               break;
            case R.id.menu_receber_provas:
                Intent irBaixarProva = new Intent(this, ProvasActivity.class);
                startActivity(irBaixarProva);
                break;
            case R.id.menu_google_maps:
                Intent irMaps = new Intent(this, MapaActivity.class);
                startActivity(irMaps);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = getAlunoPosition(info.position);

        MenuItem ligar = menu.add("Ligar");
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    String[] permissoes = {Manifest.permission.CALL_PHONE};
                    ActivityCompat.requestPermissions(MainActivity.this, permissoes, REQUEST_PHONE);
                }else {
                    Intent irLigacao = new Intent(Intent.ACTION_CALL);
                    irLigacao.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(irLigacao);
                }


                return false;
            }
        });

        MenuItem sms = menu.add("Enviar SMS");
        Intent irSms = new Intent(Intent.ACTION_VIEW);
        irSms.setData(Uri.parse("sms:" + aluno.getTelefone()));
        sms.setIntent(irSms);

        MenuItem mapa = menu.add("Endereço");
        Intent irMapa = new Intent(Intent.ACTION_VIEW);
        irMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        mapa.setIntent(irMapa);

        MenuItem site = menu.add("Visitar Site");
        Intent irSite = new Intent(Intent.ACTION_VIEW);
        irSite.setData(Uri.parse(parseUrl(aluno.getSite())));
        site.setIntent(irSite);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                AlunoDAO dao = new AlunoDAO(MainActivity.this);
                dao.deletar(aluno);
                dao.close();

                buscarAlunos();

                alunoSync.deletarSync(aluno);

                return false;
            }
        });
    }

    @Nullable
    private String parseUrl(String url) {
        if(url != null && !url.startsWith("http://")){
            url = "http://" + url;
        }
        return url;
    }

    private Aluno getAlunoPosition(int position) {
        return (Aluno) listaAlunosView.getItemAtPosition(position);
    }



    private void buscarAlunos() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscarAlunos();
        dao.close();

        for (Aluno aluno: alunos) {

            ObjectMapper om = new ObjectMapper();
            try {
                String alunoJson = om.writeValueAsString(aluno);
                Log.i("###### ALUNO: ", alunoJson);
            } catch (JsonProcessingException e) {

            }

        }

        AlunoAdapter alunoAdapter = new AlunoAdapter(this, alunos);
        listaAlunosView.setAdapter(alunoAdapter);
    }


}
