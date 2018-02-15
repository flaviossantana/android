package br.com.alura.agenda.helper;

import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.RatingBar;

import br.com.alura.agenda.FormularioActivity;
import br.com.alura.agenda.R;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by f1avi on 14/02/2018.
 */

public class FormularioHelper {

    private Aluno aluno;

    private final FormularioActivity activity;
    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoSite;
    private final EditText campoTelefone;
    private final RatingBar campoNota;

    public FormularioHelper(FormularioActivity activity) {
        this.aluno = new Aluno();
        this.activity = activity;
        campoNome = getEditText(R.id.cadastro_nome);
        campoEndereco = getEditText(R.id.cadastro_endereco);
        campoSite = getEditText(R.id.cadastro_site);
        campoTelefone = getEditText(R.id.cadastro_telefone);
        campoNota = getRatingBar(R.id.cadastro_nota);
    }

    public Aluno getAluno(){
        Aluno aluno = new Aluno();
        aluno.setEndereco(getEditTexValue(campoEndereco));
        aluno.setNome(getEditTexValue(campoNome));
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        aluno.setSite(getEditTexValue(campoSite));
        aluno.setTelefone(getEditTexValue(campoTelefone));
        return aluno;
    }

    public void setAluno(Aluno aluno){
        campoEndereco.setText(aluno.getEndereco());
        campoNome.setText(aluno.getNome());
        campoNota.setProgress(aluno.getNota().intValue());
        campoSite.setText(aluno.getSite());
        campoTelefone.setText(aluno.getTelefone());
        this.aluno = aluno;
    }

    @NonNull
    private String getEditTexValue(EditText editText) {
        return editText.getText().toString();
    }

    private EditText getEditText(int idCampo) {
        return activity.findViewById(idCampo);
    }

    private RatingBar getRatingBar(int idCampo) {
        return activity.findViewById(idCampo);
    }
}
