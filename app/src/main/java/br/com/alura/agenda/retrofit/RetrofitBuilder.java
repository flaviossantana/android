package br.com.alura.agenda.retrofit;

import br.com.alura.agenda.service.AlunoService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by f1avi on 23/02/2018.
 */

public class RetrofitBuilder {

    public static final String URL_BASE = "http://192.168.0.106:8080/api/";
    private final Retrofit retrofit;

    public RetrofitBuilder() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder().baseUrl(URL_BASE)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public AlunoService getAlunoService(){
        return retrofit.create(AlunoService.class);
    }

}
