package com.example.snacc

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.snacc.databinding.ActivitySigninBinding

class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🧩 Safe area handling
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ✨ Fade + slide in untuk container teks
        val fadeSlideIn = AnimationUtils.loadAnimation(this, R.anim.fade_slide_in)
        binding.textContainer.startAnimation(fadeSlideIn)

        // 🌈 Glow lembut di title
        val glowPulse = AnimationUtils.loadAnimation(this, R.anim.glow_pulse)
        binding.titleText.startAnimation(glowPulse)

        // 🟣 Tombol muncul smooth dari bawah
        val fadeButtons = AnimationUtils.loadAnimation(this, R.anim.fade_slide_in).apply {
            startOffset = 400 // muncul agak telat biar elegan
        }
        binding.registerButton.startAnimation(fadeButtons)
        binding.loginButton.startAnimation(fadeButtons)

        // 🚀 Aksi tombol
        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        // 🚪 Tombol Back
        binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            finish() // biar SigninActivity nggak numpuk di back stack
        }
    }
}
