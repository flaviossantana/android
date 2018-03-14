package br.com.alura.agenda.dao.upgrade;

import android.database.sqlite.SQLiteDatabase;

/**
 * Incluindo nova coluna SINCRONIZADO na tabela de aluno, com um valor default como 0 (ZERO).
 * Created by f1avi on 13/03/2018.
 */

public class Versao_3 {

    public static void upgrade(SQLiteDatabase db){
        alterAlunosAddSincronizar(db);
    }

    private static void alterAlunosAddSincronizar(SQLiteDatabase db) {
        String sql = "ALTER TABLE Alunos ADD COLUMN sincronizado DEFAULT 0";
        db.execSQL(sql);
    }
}
