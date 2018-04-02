package br.com.alura.agenda.dao.upgrade;

import android.database.sqlite.SQLiteDatabase;

/**
 * Incluindo nova coluna DESATIVADO na tabela de aluno, com um valor default como 0 (ZERO).
 * Created by f1avi on 13/03/2018.
 */

public class Versao_4 {

    public static void upgrade(SQLiteDatabase db){
        alterAlunosAddDesativado(db);
    }

    private static void alterAlunosAddDesativado(SQLiteDatabase db) {
        String sql = "ALTER TABLE Alunos ADD COLUMN desativado DEFAULT 0";
        db.execSQL(sql);
    }
}
