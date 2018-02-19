package br.com.alura.agenda;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.alura.agenda.modelo.Prova;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalhesProvaFragment extends Fragment {

    private TextView campoMateria;
    private TextView campoData;
    private ListView listaTopicos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhes_prova, container, false);

        campoMateria = view.findViewById(R.id.topico_prova_materia);
        campoData = view.findViewById(R.id.topico_prova_data);
        listaTopicos = view.findViewById(R.id.prova_topicos);

        Bundle params = getArguments();
        if(params != null){
            Prova prova = (Prova) params.getSerializable("prova");
            popularCamposCom(prova);
        }

        return view;
    }

    public void popularCamposCom(Prova prova){

        campoMateria.setText(prova.getMateria());
        campoData.setText(prova.getData());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, prova.getTopicos());
        listaTopicos.setAdapter(adapter);

    }

}
