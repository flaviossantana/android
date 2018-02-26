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
        String sql = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL, caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion){
            case 1:
                sql = "ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT";
                db.execSQL(sql);
            case 2:
                sql = "CREATE TABLE Alunos_novo (id CHAR(36) PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL, caminhoFoto TEXT);";
                db.execSQL(sql);
                sql = "INSERT INTO Alunos_novo (id, nome, endereco, telefone, site, nota, caminhoFoto) SELECT id, nome, endereco, telefone, site, nota, caminhoFoto FROM Alunos";
                db.execSQL(sql);
                sql = "DROP TABLE Alunos";
                db.execSQL(sql);
                sql = "ALTER TABLE Alunos_novo RENAME TO Alunos";
                db.execSQL(sql);
            case 3:
                sql = "SELECT * FROM Alunos";
                Cursor cursor = db.rawQuery(sql, null);
                List<Aluno> alunos = populaAlunos(cursor);

                sql = "UPDATE Alunos SET id=? WHERE id=?";
                for (Aluno aluno : alunos) {
                    db.execSQL(sql, new String[] {geraUUID(), aluno.getId()});
                }

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


        if(isAlteracao(aluno)){
            String[] whereParams = {aluno.getId().toString()};
            db.update("Alunos", dados, "id = ?", whereParams);
            return;
        }

        aluno.setId(geraUUID());
        dados.put("id", aluno.getId());
        db.insert("Alunos", null, dados);
    }

    private boolean isAlteracao(Aluno aluno) {
        return aluno.getId() != null;
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
}
