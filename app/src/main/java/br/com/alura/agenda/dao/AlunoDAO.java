package br.com.alura.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by f1avi on 14/02/2018.
 */

public class AlunoDAO extends SQLiteOpenHelper {


    private static final int NEW_VERSION = 1;
    private static final int OLD_VERSION = 1;

    public AlunoDAO(Context context) {
        super(context, "agenda", null, NEW_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS Alunos";
        db.execSQL(sql);
        onCreate(db);
    }

    public void inserir(Aluno aluno){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("nota", aluno.getNota());
        dados.put("site", aluno.getSite());
        dados.put("telefone", aluno.getTelefone());

        db.insert("Alunos", null, dados);
    }
}
