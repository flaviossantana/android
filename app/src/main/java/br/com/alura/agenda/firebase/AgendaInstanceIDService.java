package br.com.alura.agenda.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import br.com.alura.agenda.retrofit.RetrofitBuilder;
import br.com.alura.agenda.service.DispositivoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by f1avi on 28/02/2018.
 */

public class AgendaInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FIREBASE --->", "Refreshed token: " + refreshedToken);
        enviarTokenServidor(refreshedToken);
    }

    private void enviarTokenServidor(final String token) {

        DispositivoService dispositivoService = new RetrofitBuilder().getDispositivoService();
        Call<Void> call = dispositivoService.enviarToken(token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("Token enviado: ", token);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("Token falhou: ", token);
            }
        });

    }

}
