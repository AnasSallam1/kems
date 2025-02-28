package co.atlasgames.kems.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import co.atlasstudios.kems.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val splashDelay = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Use Handler to delay the transition to the main activity
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close the splash activity to avoid it being added to the back stack
        }, splashDelay.toLong())
    }
}