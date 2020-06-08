package xpertii.prasathinterview

import android.content.Intent
import android.os.Bundle
import android.os.Handler

import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DISPLAY_LENGTH = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportActionBar != null)
            supportActionBar?.hide()
        setContentView(R.layout.splashscreenlayout)
        Handler().postDelayed({
            Handler().postDelayed({
                    val mainIntent =
                        Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                }, SPLASH_DISPLAY_LENGTH.toLong())
            }, 500)

    }
}