package br.com.alura.agenda.retrofit;

import br.com.alura.agenda.service.AlunoService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by f1avi on 23/02/2018.
 */

public class RetrofitBuilder {

    private final Retrofit retrofit;

    public RetrofitBuilder() {
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.106:8080/api/").addConverterFactory(JacksonConverterFactory.create()).build();
    }

    public AlunoService getAlunoService(){
        return retrofit.create(AlunoService.class);
    }

}
