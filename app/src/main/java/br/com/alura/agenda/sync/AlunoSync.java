package br.com.alura.agenda.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.alura.agenda.MainActivity;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.dto.AlunoSyncDTO;
import br.com.alura.agenda.event.AtualizarListaAlunoEvent;
import br.com.alura.agenda.modelo.Aluno;
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

    private void sincronizaInternos(){
        final AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.naoSincronizados();

        ObjectMapper om = new ObjectMapper();
        try {
            String alunoJson = om.writeValueAsString(alunos);
            Log.i("###### LISTA ALUNO: ", alunoJson);
        } catch (JsonProcessingException e) {

        }

        Call<AlunoSyncDTO> call = new RetrofitBuilder().getAlunoService().atualizar(alunos);
        call.enqueue(new Callback<AlunoSyncDTO>() {
            @Override
            public void onResponse(Call<AlunoSyncDTO> call, Response<AlunoSyncDTO> response) {
                AlunoSyncDTO alunoSync = response.body();
                dao.sincronizar(alunoSync.getAlunos());
                dao.close();
            }

            @Override
            public void onFailure(Call<AlunoSyncDTO> call, Throwable t) {
                Log.e("##ALUNO NÃO SYNC: ", t.getMessage());
            }
        });

    }

    public void buscarTodos(){
        if(alunoPeferences.temVersao()){
            buscarNovos();
        }else {
            sincronizarAlunos();
        }
    }

    private void buscarNovos(){
        Call<AlunoSyncDTO> call = new RetrofitBuilder().getAlunoService().novos(alunoPeferences.getVersao());
        call.enqueue(buscaAlunoCallback());

    }

    private void sincronizarAlunos() {
        Call<AlunoSyncDTO> call = new RetrofitBuilder().getAlunoService().lista();
        call.enqueue(buscaAlunoCallback());
    }

    @NonNull
    private Callback<AlunoSyncDTO> buscaAlunoCallback() {
        return new Callback<AlunoSyncDTO>() {
            @Override
            public void onResponse(Call<AlunoSyncDTO> call, Response<AlunoSyncDTO> response) {

                AlunoSyncDTO alunoSyncDTO = response.body();

                sincronizar(alunoSyncDTO);

                Log.e("#######VERSÃO: ", alunoPeferences.getVersao());

                bus.post(new AtualizarListaAlunoEvent());
                sincronizaInternos();
            }

            @Override
            public void onFailure(Call<AlunoSyncDTO> call, Throwable t) {
                bus.post(new AtualizarListaAlunoEvent());
                Log.e("onFailure: ", t.getMessage());
            }
        };
    }

    public void sincronizar(AlunoSyncDTO alunoSyncDTO) {

        String versao = alunoSyncDTO.getMomentoDaUltimaModificacao();

        if(isNovaVersao(versao)){

        }

        alunoPeferences.salvarVersao(versao);

        AlunoDAO dao = new AlunoDAO(context);
        dao.sincronizar(alunoSyncDTO.getAlunos());
        dao.close();
    }

    private boolean isNovaVersao(String versao) {

        if(!alunoPeferences.temVersao()){
            return false;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

        try {

            Date dataExterna = format.parse(versao);
            String versaoInterna = alunoPeferences.getVersao();
            Date dataInterna = format.parse(versaoInterna);

            return dataExterna.after(dataInterna);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void deletarSync(final Aluno aluno) {
        Call<Void> call = new RetrofitBuilder().getAlunoService().delete(aluno.getId());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                AlunoDAO dao = new AlunoDAO(context);
                dao.deletar(aluno);
                dao.close();
                Toast.makeText(context, aluno.getNome() +  " deletado com sucesso!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }
}