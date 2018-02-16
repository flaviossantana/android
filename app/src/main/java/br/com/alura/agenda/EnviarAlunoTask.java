package br.com.alura.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.converter.AlunoConverter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by flavio-ss on 16/02/2018.
 */

public class EnviarAlunoTask extends AsyncTask<Object, Object, String> {

    private final Context context;
    private ProgressDialog dialog;

    public EnviarAlunoTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde...", "Enviando Alunos...", true, true);
    }

    @Override
    protected String doInBackground(Object[] params) {

        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscarAlunos();
        dao.close();

        String json = new AlunoConverter().toJson(alunos);
        WebClient client = new WebClient();
        String resposta = client.post(json);

        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context,   resposta , Toast.LENGTH_SHORT).show();
    }
}
