package com.example.snacc

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import android.widget.TextView

class OrderActivity : AppCompatActivity() {

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        sharedPrefs = getSharedPreferences("user_session", MODE_PRIVATE)

        val tvGreeting = findViewById<TextView>(R.id.tvGreetingOrder)
        val tvOrderDetails = findViewById<TextView>(R.id.tvOrderDetails)
        val btnSend = findViewById<MaterialButton>(R.id.btnSend)

        //  ambil username dari LoginActivity (SharedPreferences)
        val username = sharedPrefs.getString("username", "Pengguna")

        //  ambil nama makanan dari DashboardActivity
        val foodName = intent.getStringExtra("FOOD_NAME") ?: "Tidak ada pesanan"

        // tampilkan di UI
        tvGreeting.text = "Halo, $username 👋"
        tvOrderDetails.text = "Kamu memesan: $foodName"

        //  tombol kirim — nanti bisa diarahkan ke ConfirmOrderActivity
        btnSend.setOnClickListener {
             val intent = Intent(this, ConfirmOrderActivity::class.java)
                startActivity(intent)

        }
    }
}
