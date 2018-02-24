package br.com.alura.agenda;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by flavio-ss on 16/02/2018.
 */

public class WebClient extends AsyncTask<String, Void, Boolean> {

    public static final String WWW_CAELUM_COM_BR_MOBILE = "https://www.caelum.com.br/mobile";
    public static final String SERVIDOR_ALUNO_FORM = "http://192.168.0.106:8080/api/aluno";

    @Override
    protected Boolean doInBackground(String... strings) {
        return null;
    }

    public String post(String json){
        return realizaConexao(WWW_CAELUM_COM_BR_MOBILE, json);
    }

    public void inserir(String json) {
        realizaConexao(SERVIDOR_ALUNO_FORM, json );


    }

    @Nullable
    private String realizaConexao(String endereco, String json) {
        try {
            URL url = new URL(endereco);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            PrintStream output = new PrintStream(connection.getOutputStream());
            output.println(json);

            Scanner scanner = new Scanner(connection.getInputStream());
            String resposta = scanner.next();
            return resposta;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



}
