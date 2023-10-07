package com.example.senai

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class cadastrar : AppCompatActivity() {

    private lateinit var textCadastrar: TextView
    private lateinit var editCurso: EditText
    private lateinit var editNomeCompleto: EditText
    private lateinit var editNascimento: EditText
    private lateinit var editMatricula: EditText
    private lateinit var editCpf: EditText
    private lateinit var btnCadastrar: Button
    private lateinit var btnMostrar: Button
    private lateinit var listView: ListView
    private lateinit var codig: ArrayList<String> // Declare codig here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar)

        textCadastrar = findViewById(R.id.textCadastrar)
        editCurso = findViewById(R.id.editcurso)
        editNomeCompleto = findViewById(R.id.editNomecompleto)
        editNascimento = findViewById(R.id.editTextDate)
        editMatricula = findViewById(R.id.editTextNumber)
        editCpf = findViewById(R.id.editTextText3)
        btnCadastrar = findViewById(R.id.btnCadastrar)
        btnMostrar = findViewById(R.id.btnMostrar)
        listView = findViewById(R.id.listview)
        codig = ArrayList()

        val dbHelper = DBHelper(this, null)

        btnCadastrar.setOnClickListener {
            val curso = editCurso.text.toString()
            val nomeCompleto = editNomeCompleto.text.toString()
            val nascimento = editNascimento.text.toString()
            val matricula = editMatricula.text.toString()
            val cpf = editCpf.text.toString()

            if (curso.isBlank() || nomeCompleto.isBlank() || nascimento.isBlank() || matricula.isBlank() || cpf.isBlank()) {
                Toast.makeText(this@cadastrar, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
            } else {
                val insertedId = dbHelper.addAluno(curso, nomeCompleto, nascimento, matricula, cpf)

                if (insertedId != -1L) {
                    Toast.makeText(this@cadastrar, "Aluno cadastrado com sucesso.", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@cadastrar, "Erro ao cadastrar aluno.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        btnMostrar.setOnClickListener {
            val db = DBHelper(this, null)
            val cursor = db.getnome()
            cursor!!.moveToFirst()
            cursor.getString(1)

            val usuario = arrayListOf<String>()

            usuario.add("Nome:" + cursor.getString(1) + " CPF: " + cursor.getString(2))
            codig.add(cursor.getString(0))

            while (cursor.moveToNext()) {
                usuario.add("Nome:" + cursor.getString(1) + " CPF: " + cursor.getString(2))
                codig.add(cursor.getString(0))
            }

            val arrayAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1, usuario
            )
            listView.adapter = arrayAdapter
        }

        listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position: Int, ind: Long ->
            try {
                val db = DBHelper(this, null)
                var num = codig[position]

                val cursor2 = db.getDados(num)
                cursor2!!.moveToFirst()
                editCurso.setText(cursor2.getString(1).toString())
                editNomeCompleto.setText(cursor2.getString(2).toString())
                editNascimento.setText(cursor2.getString(3).toString())
                editMatricula.setText(cursor2.getString(4).toString())
                editCpf.setText(cursor2.getString(5).toString())
                Toast.makeText(
                    applicationContext,
                    "Position :$num", Toast.LENGTH_LONG
                ).show()
            } catch (e: Exception) {
                e.printStackTrace() // Isso imprimirá o erro no logcat
                Toast.makeText(
                    applicationContext,
                    "Erro: ${e.message}", Toast.LENGTH_LONG
                ).show() // Isso mostrará o erro em um Toast
            }
        }


    }

}
