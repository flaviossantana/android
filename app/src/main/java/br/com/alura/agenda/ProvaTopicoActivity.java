package br.com.alura.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;

import br.com.alura.agenda.modelo.Prova;

public class ProvaTopicoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prova_topico);

        Intent intent = getIntent();
        Prova prova = (Prova) intent.getSerializableExtra("prova");

        TextView campoMateria = findViewById(R.id.topico_prova_materia);
        campoMateria.setText(prova.getMateria());

        TextView campoData = findViewById(R.id.topico_prova_data);
        campoData.setText(prova.getData());

        ListView listaTopicos = findViewById(R.id.prova_topicos);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, prova.getTopicos());
        listaTopicos.setAdapter(adapter);

    }
}
