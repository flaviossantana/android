package br.com.alura.agenda.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by flavio-ss on 16/02/2018.
 */


public class AlunoConverter {

    public String toListJson(List<Aluno> alunos)  {

        JSONStringer json = new JSONStringer();

        try {

            json.object().key("list").array().object().key("aluno").array();

            for (Aluno aluno : alunos) {
                json.object();
                json.key("nome").value(aluno.getNome());
                json.key("nota").value(aluno.getNota());
                json.endObject();
            }

            json.endArray()
                .endObject()
                .endArray()
                .endObject();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();
    }

    public String alunoToJson(Aluno aluno) {

        JSONStringer json = new JSONStringer();

        try {
            json.object().key("nome").value(aluno.getNome())
                         .key("endereco").value(aluno.getEndereco())
                         .key("site").value(aluno.getSite())
                         .key("telefone").value(aluno.getTelefone())
                         .key("nota").value(aluno.getNota())
                         .endObject();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();
    }
}
