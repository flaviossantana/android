package br.com.alura.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.alura.agenda.modelo.Prova;

public class ProvaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prova);

        List<String> topicosPortugues = Arrays.asList("Sujeito", "Objeto Direto", "Objeto Indireto");
        Prova provaPortugues = new Prova("Portugues", "25/01/2018", topicosPortugues);

        List<String> topicosMatematica = Arrays.asList("Equações de Segundo Grau", "Trigonometria");
        Prova provaMatematica = new Prova("Matematica", "27/01/2018", topicosMatematica);

        final List<Prova> provas = Arrays.asList(provaPortugues, provaMatematica);

        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(this, android.R.layout.simple_expandable_list_item_1, provas);

        ListView lista =  findViewById(R.id.prova_lista);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Toast.makeText(ProvaActivity.this, "Clicou " + prova.getMateria() +"!", Toast.LENGTH_SHORT).show();

                Intent irTopicos = new Intent(ProvaActivity.this, ProvaTopicoActivity.class);
                irTopicos.putExtra("prova", prova);
                startActivity(irTopicos);

            }
        });

    }

}
