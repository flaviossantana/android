package br.com.alura.agenda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class FormularioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
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
                Toast.makeText(FormularioActivity.this, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();

                String nome = getEditTextValue(R.id.cadastro_nome);
                String endereco = getEditTextValue(R.id.cadastro_endereco);
                String telefone = getEditTextValue(R.id.cadastro_telefone);
                String site = getEditTextValue(R.id.cadastro_site);

                RatingBar rt = findViewById(R.id.cadastro_nota);

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getEditTextValue(int idCampo) {
        EditText editText = findViewById(idCampo);
        return editText.getText().toString();
    }
}
