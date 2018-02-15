package br.com.alura.agenda;

import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class MainActivity extends AppCompatActivity {

    private ListView listaAlunosView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaAlunosView = findViewById(R.id.lista_alunos);

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

    }

    @Override
    protected void onResume() {
        super.onResume();
        buscarAlunos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = getAlunoPosition(info.position);

        MenuItem site = menu.add("Visitar Site");
        Intent irSite = new Intent(Intent.ACTION_VIEW);
        irSite.setData(Uri.parse(parseUrl(aluno.getSite())));
        site.setIntent(irSite);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                deletarAluno(aluno);
                buscarAlunos();

                Toast.makeText(MainActivity.this, aluno.getNome() +  " deletado com sucesso!", Toast.LENGTH_SHORT).show();
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

    private void deletarAluno(Aluno aluno) {
        AlunoDAO dao = new AlunoDAO(MainActivity.this);
        dao.deletar(aluno);
        dao.close();
    }

    private void buscarAlunos() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscarAlunos();
        dao.close();

        ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);
        listaAlunosView.setAdapter(adapter);
    }


}
