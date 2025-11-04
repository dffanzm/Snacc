package com.example.snacc

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.snacc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageView = binding.bgImage

        // 🌫️ Blur anim (kalau Android 12+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            imageView.setRenderEffect(RenderEffect.createBlurEffect(25f, 25f, Shader.TileMode.CLAMP))

            val blurAnim = ValueAnimator.ofFloat(25f, 0f)
            blurAnim.duration = 2000
            blurAnim.addUpdateListener {
                val value = it.animatedValue as Float
                imageView.setRenderEffect(
                    RenderEffect.createBlurEffect(value, value, Shader.TileMode.CLAMP)
                )
            }
            blurAnim.start()

        } else {
            // 🌈 Fallback: animasi fade-in pelan aja
            imageView.alpha = 0f
            imageView.animate()
                .alpha(1f)
                .setDuration(1500)
                .start()
        }

        // ✨ Animasi teks muncul (fade + slide)
        val fadeSlideIn = AnimationUtils.loadAnimation(this, R.anim.fade_slide_in)
        binding.textContainer.startAnimation(fadeSlideIn)

        // 🌞 Animasi glow halus di title & subtitle
        val glowAnim = AnimationUtils.loadAnimation(this, R.anim.glow_pulse)
        binding.titleText.startAnimation(glowAnim)

        val glowSlow = AnimationUtils.loadAnimation(this, R.anim.glow_pulse).apply {
            duration = 2500
        }
        binding.subtitleText.startAnimation(glowSlow)

        // 🧭 Tombol navigasi ke login
        binding.startButton.setOnClickListener {
            startActivity(Intent(this, SigninActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}
