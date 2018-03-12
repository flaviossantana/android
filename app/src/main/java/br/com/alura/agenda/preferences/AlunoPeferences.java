package br.com.alura.agenda.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by f1avi on 12/03/2018.
 */

public class AlunoPeferences {

    private static final String ALUNO_PEFERENCES = "br.com.alura.agenda.preference.AlunoPeferences";
    private static final String VERSÃO_DO_DADO = "versão_do_dado";
    private final Context context;

    public AlunoPeferences(Context context) {
        this.context = context;
    }

    public void salvarVersao(String versao){
        SharedPreferences preferences = getPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(VERSÃO_DO_DADO, versao);
        editor.commit();
    }

    public String getVersao(){
        SharedPreferences preferences = getPreferences();
        return preferences.getString(VERSÃO_DO_DADO, "");
    }

    private SharedPreferences getPreferences() {
        return context.getSharedPreferences(ALUNO_PEFERENCES, context.MODE_PRIVATE);
    }

}
