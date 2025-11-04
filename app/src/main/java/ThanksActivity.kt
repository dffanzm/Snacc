package com.example.snacc

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.snacc.databinding.ActivityThanksBinding

class ThanksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThanksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🚪 Hilangin title bar → biar modal feel
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setFinishOnTouchOutside(false)

        binding = ActivityThanksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🌫️ Blur lembut di background (Android 12+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            window.decorView.setRenderEffect(
                RenderEffect.createBlurEffect(25f, 25f, Shader.TileMode.CLAMP)
            )
        } else {
            // Fallback elegan (transparan gelap)
            binding.mainThanks.background.alpha = 200
        }

        // 🎞️ Animasi masuk: fade + slide-up
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.fade_slide_in)
        binding.mainThanks.startAnimation(slideUp)

        // 🧾 Ambil data nama dari ConfirmOrderActivity
        val name = intent.getStringExtra("FULL_NAME") ?: "User"
        binding.tvThanks.text = "Terima kasih, $name! 🎉"

        // 🩶 Tombol Tutup → fade out mulus
        binding.btnClose.setOnClickListener {
            val fadeOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
            binding.mainThanks.startAnimation(fadeOut)
            binding.mainThanks.postDelayed({
                finish()
                overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in)
            }, 250)
        }
    }
}
