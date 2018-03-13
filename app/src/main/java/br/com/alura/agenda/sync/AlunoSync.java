package br.com.alura.agenda.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.event.AtualizarListaAlunoEvent;
import br.com.alura.agenda.preferences.AlunoPeferences;
import br.com.alura.agenda.retrofit.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoSync {

    private final Context context;
    private EventBus bus = EventBus.getDefault();
    private AlunoPeferences alunoPeferences;

    public AlunoSync(Context context) {
        this.context = context;
        alunoPeferences = new AlunoPeferences(context);
    }

    public void buscarTodos(){
        if(alunoPeferences.temVersao()){
            buscarNovos();
        }else {
            sincronizarAlunos();
        }
    }

    private void buscarNovos(){
        Call<br.com.alura.agenda.dto.AlunoSync> call = new RetrofitBuilder().getAlunoService().novos(alunoPeferences.getVersao());
        call.enqueue(buscaAlunoCallback());

    }

    private void sincronizarAlunos() {
        Call<br.com.alura.agenda.dto.AlunoSync> call = new RetrofitBuilder().getAlunoService().lista();
        call.enqueue(buscaAlunoCallback());
    }

    @NonNull
    private Callback<br.com.alura.agenda.dto.AlunoSync> buscaAlunoCallback() {
        return new Callback<br.com.alura.agenda.dto.AlunoSync>() {
            @Override
            public void onResponse(Call<br.com.alura.agenda.dto.AlunoSync> call, Response<br.com.alura.agenda.dto.AlunoSync> response) {
                br.com.alura.agenda.dto.AlunoSync alunoSync = response.body();
                AlunoDAO dao = new AlunoDAO(context);
                dao.inserir(alunoSync.getAlunos());

                String versao = alunoSync.getMomentoDaUltimaModificacao();

                alunoPeferences.salvarVersao(versao);

                Log.e("#######VERSÃO: ", alunoPeferences.getVersao());

                bus.post(new AtualizarListaAlunoEvent());
            }

            @Override
            public void onFailure(Call<br.com.alura.agenda.dto.AlunoSync> call, Throwable t) {
                bus.post(new AtualizarListaAlunoEvent());
                Log.e("onFailure: ", t.getMessage());
            }
        };
    }
}