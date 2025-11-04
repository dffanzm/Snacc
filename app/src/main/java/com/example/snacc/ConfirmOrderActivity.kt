package com.example.snacc

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.snacc.databinding.ActivityConfirmOrderBinding

class ConfirmOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🛒 Ambil data pesanan dari intent (kalau ada)
        val foodName = intent.getStringExtra("FOOD_NAME")
        val foodDesc = intent.getStringExtra("FOOD_DESC")

        // Tampilkan di Toast sekadar konfirmasi
        Toast.makeText(this, "Pesanan: $foodName", Toast.LENGTH_SHORT).show()

        // 🚀 Tombol Kirim Pesanan
        binding.btnConfirmOrder.setOnClickListener {
            val fullName = binding.etFullName.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()
            val landmark = binding.etLandmark.text.toString().trim()

            // Validasi form
            if (fullName.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Isi nama dan alamat dulu ya 😅", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Intent ke ThanksActivity
            val intent = Intent(this, ThanksActivity::class.java).apply {
                putExtra("FULL_NAME", fullName)
                putExtra("ADDRESS", address)
                putExtra("LANDMARK", landmark)
                putExtra("FOOD_NAME", foodName)
                putExtra("FOOD_DESC", foodDesc)
            }

            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}
