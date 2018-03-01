package br.com.alura.agenda.service;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by f1avi on 28/02/2018.
 */

public interface DispositivoService {

    @POST("firebase/dispositivo")
    Call<Void> enviarToken(@Header("token") String token);

}
