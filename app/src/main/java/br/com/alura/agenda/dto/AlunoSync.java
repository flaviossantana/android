package br.com.alura.agenda.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by f1avi on 26/02/2018.
 */

public class AlunoSync {

    private List<Aluno> alunos;
    private String momentoDaUltimaModificacao;

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public String getMomentoDaUltimaModificacao() {
        return momentoDaUltimaModificacao;
    }
}
