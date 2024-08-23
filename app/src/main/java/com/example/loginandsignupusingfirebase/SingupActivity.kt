package com.example.loginandsignupusingfirebase

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginandsignupusingfirebase.databinding.ActivitySingupBinding
import com.google.firebase.auth.FirebaseAuth

class SingupActivity : AppCompatActivity() {

    private val binding: ActivitySingupBinding by lazy {
        ActivitySingupBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // initialize firebase auth
        auth= FirebaseAuth.getInstance()

        binding.singin.setOnClickListener {
            startActivity(Intent(this, LogIn::class.java))
            finish()
        }

        binding.register.setOnClickListener {

          // get text for edit text filed
            val email = binding.email.text.toString()
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            val rpassword = binding.rpassword.text.toString()

            // check if any filed is black
            if (email.isEmpty()||username.isEmpty()||password.isEmpty()||rpassword.isEmpty()){
                Toast.makeText(this, "Please Fill All Ahe Details", Toast.LENGTH_SHORT).show()
            } else if (password != rpassword){
                Toast.makeText(this, "Password Not Match", Toast.LENGTH_SHORT).show()
            }else{
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this){task ->
                        if (task.isSuccessful){
                            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        } else{
                            Toast.makeText(this, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }


    }
}