package br.com.alura.agenda.modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by flavio-ss on 19/02/2018.
 */

public class Prova implements Serializable {

    public String materia;
    public String data;
    private List<String> topicos;

    public Prova() {

    }

    public Prova(String materia, String data, List<String> topicos) {
        this.materia = materia;
        this.data = data;
        this.topicos = topicos;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<String> getTopicos() {
        return topicos;
    }

    public void setTopicos(List<String> topicos) {
        this.topicos = topicos;
    }

    @Override
    public String toString() {
        return this.materia;
    }
}
