package br.com.alura.agenda.retrofit;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Map;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.dto.AlunoSync;
import br.com.alura.agenda.event.AtualizarListaAlunoEvent;

/**
 * Created by f1avi on 28/02/2018.
 */

public class AgendaMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> mensagem = remoteMessage.getData();
        Log.i("Msg recebida: ", String.valueOf(mensagem));
        converterParaAlunos(mensagem);
    }

    private void converterParaAlunos(Map<String, String> mensagem) {
        final String ALUNO_SYNC_KEY = "alunoSync";
        if(mensagem.containsKey(ALUNO_SYNC_KEY)){
            String json = mensagem.get(ALUNO_SYNC_KEY);
            try {
                AlunoSync alunoSync = new ObjectMapper().readValue(json, AlunoSync.class);
                AlunoDAO dao = new AlunoDAO(this);
                dao.inserir(alunoSync.getAlunos());
                dao.close();

                EventBus eventBus = EventBus.getDefault();
                eventBus.post(new AtualizarListaAlunoEvent());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
