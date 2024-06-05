package com.example.ebm.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.ebm.R
import com.example.ebm.databinding.ActivityMainBinding
import com.example.ebm.ui.search.activity.SearchActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val authViewModel: MainViewModel by viewModel()
    private var isSignIn: Boolean = true
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        authViewModel.registerResult.observe(this, Observer { result ->
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            if(result.contains("Registration Successful")){
                handler.postDelayed({startActivity(Intent(this, SearchActivity::class.java))}, DELAY)
            }
        })

        authViewModel.loginResult.observe(this, Observer { result ->
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            if(result.contains("Login Successful")){
                handler.postDelayed({startActivity(Intent(this, SearchActivity::class.java))}, DELAY)
            }
        })
        val continueButton = findViewById<TextView>(R.id.continue_button)
        val loginField = findViewById<EditText>(R.id.login_et)
        val passwordField = findViewById<EditText>(R.id.password_et)
        val additionalPasswordField = findViewById<EditText>(R.id.additional_password_et)
        continueButton.setOnClickListener {
            when(isSignIn){
                true -> {
                    val username = loginField.text.toString()
                    val password = passwordField.text.toString()
                    authViewModel.login(username, password)
                }
                false -> {
                    val username = loginField.text.toString()
                    val password = passwordField.text.toString()
                    val additionalPassword = additionalPasswordField.text.toString()
                    if(password.equals(additionalPassword)){
                        authViewModel.register(username, password)
                    }else{
                        Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.registerButton.setOnClickListener {
            when(isSignIn){
                true -> { renderSignUp(); isSignIn = false}
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
    companion object{
        const val DELAY = 1000L
    }

}