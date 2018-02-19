package br.com.alura.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.alura.agenda.modelo.Prova;

/**
 * Created by flavio-ss on 19/02/2018.
 */

public class ListaProvaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_provas, container, false);

        List<String> topicosPortugues = Arrays.asList("Sujeito", "Objeto Direto", "Objeto Indireto");
        Prova provaPortugues = new Prova("Portugues", "25/01/2018", topicosPortugues);

        List<String> topicosMatematica = Arrays.asList("Equações de Segundo Grau", "Trigonometria");
        Prova provaMatematica = new Prova("Matematica", "27/01/2018", topicosMatematica);

        final List<Prova> provas = Arrays.asList(provaPortugues, provaMatematica);

        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(), android.R.layout.simple_expandable_list_item_1, provas);

        ListView lista =  view.findViewById(R.id.prova_lista);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "Clicou " + prova.getMateria() +"!", Toast.LENGTH_SHORT).show();

                Intent irTopicos = new Intent(getContext(), ProvaTopicoActivity.class);
                irTopicos.putExtra("prova", prova);
                startActivity(irTopicos);

            }
        });

        return view;
    }
}
