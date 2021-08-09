package com.draco.ludere.views
import android.widget.RelativeLayout
import java.util.*
import kotlin.system.exitProcess
import android.net.Uri
import android.content.Intent
import android.app.Activity
import android.app.Service
import android.content.DialogInterface
import android.hardware.input.InputManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.draco.ludere.R
import com.draco.ludere.gamepad.GamePad
import com.draco.ludere.gamepad.GamePadConfig
import com.draco.ludere.input.ControllerInput
import com.draco.ludere.retroview.RetroView
import com.draco.ludere.utils.RetroViewUtils
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import ir.tapsell.sdk.bannerads.TapsellBannerType
import ir.tapsell.sdk.bannerads.TapsellBannerView
import ir.tapsell.sdk.*
import ir.tapsell.sdk.TapsellAdRequestOptions.CACHE_TYPE_STREAMED


class MainActivity : AppCompatActivity() {
    var ad: TapsellAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    /** Called when the user touches the button */
    fun start(view: View) {
        // Do something in response to button click
         requestInterstitialBannerAd(AdType.BANNER) 
         requestInterstitialBannerAd(AdType.VIDEO)
         showAd()
               //     startActivity(Intent(this@MainActivity, InterstitialActivity::class.java))
  /*                  
        val start_the_game_button = findViewById(R.id.start_the_game_button) as Button
        start_the_game_button.isEnabled = false
        start_the_game_button.visibility = View.INVISIBLE
        val comments = findViewById(R.id.comments) as Button
        comments.isEnabled = false
        comments.visibility = View.INVISIBLE
        val game_page = findViewById(R.id.game_page) as Button
        game_page.isEnabled = false
        game_page.visibility = View.INVISIBLE
        val exit_button = findViewById(R.id.exit_button) as Button
        exit_button.isEnabled = false
        exit_button.visibility = View.INVISIBLE
        val send_email = findViewById(R.id.send_email) as Button
        send_email.isEnabled = false
        send_email.visibility = View.INVISIBLE
        val relative = findViewById(R.id.relative) as RelativeLayout
        relative.setBackgroundResource(0)
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
        */
    }

    fun sendMsg(view: View) {
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("myket://comment?id=com.draco.ludere.Test")
        startActivity(openURL)
    }

    fun sendingEmail(view: View) {

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto: siavashiranpak@gmail.com")
        intent.putExtra(Intent.EXTRA_SUBJECT, "نظر دهی")
        startActivity(intent)

    }

    fun goToPage(view: View) {
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("myket://details?id=com.draco.ludere.Test")
        startActivity(openURL)
    }

    fun exit_game(view: View) {
        this@MainActivity.finish()
        exitProcess(0)
    }

    
        private fun requestInterstitialBannerAd(type: AdType) {
        val options = TapsellAdRequestOptions(CACHE_TYPE_STREAMED)
        Tapsell.requestAd(this@MainActivity,
            if (type == AdType.BANNER) "610ed8bc35114c6ff3a596ee" else
                "610ecc7d260bc85635a14601", options,
            object : TapsellAdRequestListener() {
                override fun onAdAvailable(ad: TapsellAd?) {
                    if (isDestroyed)
                        return

                    this@MainActivity.ad = ad
                    btnShowAd.isEnabled = true
                }

                override fun onExpiring(ad: TapsellAd?) {
                    TODO("not implemented")
                }

                override fun onNoAdAvailable() {
                    TODO("not implemented")
                }

                override fun onError(str: String?) {
                    TODO("not implemented")
                }

                override fun onNoNetwork() {
                    TODO("not implemented")
                }
            })
    }

    private fun showAd() {
        ad?.let {
            val showOptions = TapsellShowOptions()
            showOptions.rotationMode = TapsellShowOptions.ROTATION_LOCKED_LANDSCAPE
            it.show(this@MainActivity, showOptions, object : TapsellAdShowListener() {
                override fun onOpened(ad: TapsellAd) {
                    Log.e("InterstitialActivity", "on ad opened")
                }

                override fun onClosed(ad: TapsellAd) {
                    Log.e("InterstitialActivity", "on ad closed")
                }
            })
        } ?: run {
            Log.e("InterstitialActivity", "ad is not available")
        }

        btnShowAd.isEnabled = false
        ad = null
    }
}


    
}
enum class AdType {
    BANNER, VIDEO
}
