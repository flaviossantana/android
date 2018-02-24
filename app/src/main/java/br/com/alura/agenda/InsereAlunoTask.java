package br.com.alura.agenda;

import android.os.AsyncTask;

import br.com.alura.agenda.converter.AlunoConverter;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by f1avi on 23/02/2018.
 */

class InsereAlunoTask extends AsyncTask{

    private Aluno aluno;

    public InsereAlunoTask(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        String json = new AlunoConverter().alunoToJson(aluno);
        new WebClient().inserir(json);
        return null;
    }



}
