package br.com.alura.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] alunos = {"ANA HINT", "MARIA", "JOSE", "EUGENIA", "EVA", "FLAVIO", "BETO", "OSVALDO", "ANDREA", "GUSTAVO", "RUAN", "CARLOS", "JOSEFH", "PAULO", "VICTOR"};
        ListView listaAlunosView = findViewById(R.id.lista_alunos);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);
        listaAlunosView.setAdapter(adapter);

        Button incluirAlunobtn = findViewById(R.id.lista_incluir_aluno);
        incluirAlunobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent irCadastro = new Intent(MainActivity.this, FormularioActivity.class);
                startActivity(irCadastro);
            }
        });

    }
}
