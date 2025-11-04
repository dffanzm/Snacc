package com.example.snacc

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        //  Inisialisasi SharedPreferences
        sharedPrefs = getSharedPreferences("user_session", MODE_PRIVATE)

        // ️ Reset session otomatis saat app dibuka ulang (biar gak auto login)
        //  Hapus bagian ini kalau nanti udah punya tombol Logout sendiri
        sharedPrefs.edit().apply {
            putBoolean("isLoggedIn", false)
            remove("username")
            apply()
        }

        //  Cek apakah user udah login sebelumnya
        val isLoggedIn = sharedPrefs.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
            return
        }

        // padding biar ga ketabrak status bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //  Ambil semua view
        val backButton = findViewById<ImageButton>(R.id.backButton)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val googleButton = findViewById<ImageButton>(R.id.googleButton)
        val facebookButton = findViewById<ImageButton>(R.id.facebookButton)
        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val registerLink = findViewById<TextView>(R.id.registerLink)

        //  Animasi klik
        val clickAnim = AnimationUtils.loadAnimation(this, R.anim.btn_click)

        //  Dummy akun
        val dummyUsers = mapOf(
            "Dffanzm" to "Bandung22",
            "Mungkung" to "Persib",
            "AgungPermanen" to "ewinghd"
        )

        //  Tombol Back
        backButton.setOnClickListener {
            it.startAnimation(clickAnim)
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }

        //  Tombol Login
        loginButton.setOnClickListener {
            it.startAnimation(clickAnim)
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            when {
                username.isEmpty() || password.isEmpty() -> {
                    Toast.makeText(this, "Isi dulu username & password-nya ", Toast.LENGTH_SHORT).show()
                }
                dummyUsers.containsKey(username) && dummyUsers[username] == password -> {
                    Toast.makeText(this, "Login berhasil sebagai $username ", Toast.LENGTH_SHORT).show()

                    // 🔐 Simpan session login
                    sharedPrefs.edit().apply {
                        putBoolean("isLoggedIn", true)
                        putString("username", username)
                        apply()
                    }

                    // ⏩ Pindah ke DashboardActivity
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }
                else -> {
                    Toast.makeText(this, "Username atau password salah brok", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 🌐 Tombol Google
        googleButton.setOnClickListener {
            it.startAnimation(clickAnim)
            Toast.makeText(this, "Login pakai Google? hohoy", Toast.LENGTH_SHORT).show()
        }

        //  Tombol Facebook
        facebookButton.setOnClickListener {
            it.startAnimation(clickAnim)
            Toast.makeText(this, "Kalem hela euy nnti meren di lanjut", Toast.LENGTH_SHORT).show()
        }

        //  Link ke Register
        registerLink.setOnClickListener {
            it.startAnimation(clickAnim)
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}
