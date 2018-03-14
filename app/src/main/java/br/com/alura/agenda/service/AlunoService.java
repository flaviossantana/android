package br.com.alura.agenda.service;

import java.util.List;

import br.com.alura.agenda.dto.AlunoSyncDTO;
import br.com.alura.agenda.modelo.Aluno;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by f1avi on 23/02/2018.
 */

public interface AlunoService {

    @POST("aluno")
    Call<Void> insere(@Body Aluno aluno);

    @GET("aluno")
    Call<AlunoSyncDTO> lista();

    @DELETE("aluno/{id}")
    Call<Void> delete(@Path("id") String id);

    @GET("aluno/diff")
    Call<AlunoSyncDTO> novos(@Header("datahora") String versao);

    @PUT("aluno/lista")
    Call<AlunoSyncDTO> atualizar(@Body List<Aluno> alunos);
}
