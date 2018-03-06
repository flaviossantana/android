package br.com.alura.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by f1avi on 14/02/2018.
 */

public class AlunoDAO extends SQLiteOpenHelper {

    private static final int NEW_VERSION = 3;

    public AlunoDAO(Context context) {
        super(context, "agenda", null, NEW_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos (id CHAR(36) PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL, caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion){
            case 1:

        }
    }

    private String geraUUID() {
        return String.valueOf(UUID.randomUUID());
    }

    public void inserir(Aluno aluno){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("nota", aluno.getNota());
        dados.put("site", aluno.getSite());
        dados.put("telefone", aluno.getTelefone());
        dados.put("caminhoFoto", aluno.getUrlFoto());


        if(aluno.isDesativado()){
            deletar(aluno);
            return;
        }

        if(isAlteracao(aluno)){
            String[] whereParams = {aluno.getId().toString()};
            db.update("Alunos", dados, "id = ?", whereParams);
            return;
        }

        inserirIdSeNecessario(aluno);

        dados.put("id", aluno.getId());
        db.insert("Alunos", null, dados);
    }

    private void inserirIdSeNecessario(Aluno aluno) {
        if(aluno.getId() == null){
            aluno.setId(geraUUID());
        }
    }

    private boolean isAlteracao(Aluno aluno) {
        return aluno.getId() != null && existe(aluno);
    }

    public List<Aluno> buscarAlunos() {
        String sql = "SELECT * FROM Alunos;";

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Aluno> alunos = populaAlunos(c);

        c.close();
        return alunos;
    }

    @NonNull
    private List<Aluno> populaAlunos(Cursor c) {
        List<Aluno> alunos = new ArrayList<Aluno>();
        while (c.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(c.getString(c.getColumnIndex("id")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setUrlFoto(c.getString(c.getColumnIndex("caminhoFoto")));
            alunos.add(aluno);
        }
        return alunos;
    }

    public void deletar(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {aluno.getId().toString()};
        db.delete("Alunos", "id = ?", params);
    }

    public boolean ehAluno(String telefone){
        String sql = "SELECT * FROM Alunos WHERE telefone = ?";
        SQLiteDatabase db = getReadableDatabase();
        String[] params = {telefone};
        Cursor c = db.rawQuery(sql, params);
        int count = c.getCount();
        c.close();
        return count > 0;
    }

    public void inserir(List<Aluno> alunos) {

        for (Aluno aluno: alunos) {
            inserir(aluno);
        }

    }

    private boolean existe(Aluno aluno) {
        String sql = "SELECT id FROM Alunos WHERE id = ? LIMIT 1";
        SQLiteDatabase db = getReadableDatabase();
        String[] params = {aluno.getId()};
        Cursor c = db.rawQuery(sql, params);
        int count = c.getCount();
        c.close();
        return count > 0;
    }

}
