package com.example.senai

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, nothing: Nothing?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "CadastroAlunos.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "aluno"
        const val COLUMN_ID = "_id"
        const val COLUMN_CURSO = "curso"
        const val COLUMN_NOME = "nome"
        const val COLUMN_NASCIMENTO = "nascimento"
        const val COLUMN_MATRICULA = "matricula"
        const val COLUMN_CPF = "cpf"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableSQL = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_CURSO TEXT," +
                "$COLUMN_NOME TEXT," +
                "$COLUMN_NASCIMENTO TEXT," +
                "$COLUMN_MATRICULA TEXT," +
                "$COLUMN_CPF TEXT" +
                ")"
        db.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addAluno(curso: String, nome: String, nascimento: String, matricula: String, cpf: String): Long {
        val values = ContentValues()
        values.put(COLUMN_CURSO, curso)
        values.put(COLUMN_NOME, nome)
        values.put(COLUMN_NASCIMENTO, nascimento)
        values.put(COLUMN_MATRICULA, matricula)
        values.put(COLUMN_CPF, cpf)

        val db = this.writableDatabase
        val insertedId = db.insert(TABLE_NAME, null, values)
        db.close()
        return insertedId
    }

    fun updateAluno(id: Long, curso: String, nome: String, nascimento: String, matricula: String, cpf: String): Int {
        val values = ContentValues()
        values.put(COLUMN_CURSO, curso)
        values.put(COLUMN_NOME, nome)
        values.put(COLUMN_NASCIMENTO, nascimento)
        values.put(COLUMN_MATRICULA, matricula)
        values.put(COLUMN_CPF, cpf)

        val db = this.writableDatabase
        return db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    fun deleteAluno(id: Long): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    fun getnome(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun getDados(id: String): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?", arrayOf(id.toString()))
    }
}
