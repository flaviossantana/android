package br.com.alura.agenda.helper;

import android.widget.EditText;
import android.widget.RatingBar;

import br.com.alura.agenda.FormularioActivity;
import br.com.alura.agenda.R;

/**
 * Created by f1avi on 14/02/2018.
 */

public class FormularioHelper {

    private final FormularioActivity activity;
    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoSite;
    private final EditText campoTelefone;
    private final RatingBar campoNota;

    public FormularioHelper(FormularioActivity activity) {
        this.activity = activity;
        campoNome = getEditText(R.id.cadastro_nome);
        campoEndereco = getEditText(R.id.cadastro_endereco);
        campoSite = getEditText(R.id.cadastro_site);
        campoTelefone = getEditText(R.id.cadastro_telefone);
        campoNota = getRatingBar(R.id.cadastro_nota);
    }

    private EditText getEditText(int idCampo) {
        return activity.findViewById(idCampo);
    }

    private RatingBar getRatingBar(int idCampo) {
        return activity.findViewById(idCampo);
    }
}
