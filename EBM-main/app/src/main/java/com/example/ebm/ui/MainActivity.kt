package com.example.ebm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.ebm.R
import com.example.ebm.databinding.ActivityMainBinding
import com.example.ebm.ui.search.activity.SearchActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isSignIn: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val continueButton = findViewById<TextView>(R.id.continue_button)
        val loginField = findViewById<EditText>(R.id.login_et)
        val passwordField = findViewById<EditText>(R.id.password_et)
        continueButton.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        binding.registerButton.setOnClickListener {
            when(isSignIn){
                true -> {renderSignUp(); isSignIn = false }
                false -> {renderSignIn(); isSignIn = true}
            }
        }

    }
    fun renderSignIn(){
        binding.header.text = getString(R.string.sign_in)
        binding.additionalPasswordEt.isVisible = false
        binding.registerButton.text = getString(R.string.register_text)
    }
    fun renderSignUp(){
        binding.header.text = getString(R.string.sign_up)
        binding.additionalPasswordEt.isVisible = true
        binding.registerButton.text = getString(R.string.back_to_sign_in)
    }

}