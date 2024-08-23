package com.example.loginandsignupusingfirebase

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginandsignupusingfirebase.databinding.ActivityWelcomeScreenBinding


class WelcomeScreen : AppCompatActivity() {

    private val bindiing: ActivityWelcomeScreenBinding by lazy {
        ActivityWelcomeScreenBinding.inflate(layoutInflater)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(bindiing.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LogIn::class.java))
            finish()

        }, 3000
        )

        val welcomeText = "Welcome"
        val spnablestirng = SpannableString(welcomeText)
        spnablestirng.setSpan(ForegroundColorSpan(Color.parseColor("#FF0000")), 0, 5, 0)
        spnablestirng.setSpan(
            ForegroundColorSpan(Color.parseColor("#312222")),
            5,
            welcomeText.length,
            0
        )

        bindiing.Welcome.text = spnablestirng
    }
}