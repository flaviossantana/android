package br.com.alura.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.io.Serializable;

import br.com.alura.agenda.dao.AlunoDAO;

/**
 * Created by flavio-ss on 16/02/2018.
 */

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        String formato = (String) intent.getSerializableExtra("format");

        byte[] pdu = (byte[]) pdus[0];

        SmsMessage sms = SmsMessage.createFromPdu(pdu);
        String telefone = sms.getDisplayOriginatingAddress();

        AlunoDAO dao = new AlunoDAO(context);

        if(dao.ehAluno(telefone)){
            Toast.makeText(context,"Chegou SMS de um aluno!", Toast.LENGTH_SHORT).show();
//            MediaPlayer player = MediaPlayer.create(context, 1);
//            player.start();
        }

    }
}
