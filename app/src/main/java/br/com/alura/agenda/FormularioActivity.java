package br.com.alura.agenda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.helper.FormularioHelper;
import br.com.alura.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        helper = new FormularioHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.formulario_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_formulario_ok:

                Aluno aluno = helper.getAluno();
                AlunoDAO dao = new AlunoDAO(this);
                dao.inserir(aluno);
                dao.close();

                Toast.makeText(FormularioActivity.this, aluno.getNome() +  " salvo com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
                //break;
        }

        return super.onOptionsItemSelected(item);
    }

}
