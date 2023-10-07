package com.example.senai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import com.example.senai.R
import com.example.senai.cadastrar

class MainActivity : AppCompatActivity() {
    private lateinit var btnLogin: Button
    private lateinit var btnCadastrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin = findViewById(R.id.btnlogin)
        btnCadastrar = findViewById(R.id.btncadastrar)

        // Configurar listener de clique para o botão "com.example.senai.Cadastrar"
        btnCadastrar.setOnClickListener {
            val intent = Intent(this, cadastrar::class.java)
            startActivity(intent)
        }

        // Configurar listener de clique para o botão "Login" (se necessário)
        btnLogin.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
    }
}
