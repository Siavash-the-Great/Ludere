package com.draco.ludere.views

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ir.tapsell.sdk.*
import ir.tapsell.sdk.TapsellAdRequestOptions.CACHE_TYPE_STREAMED
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import kotlinx.android.synthetic.main.activity_interstitial.*
import com.draco.ludere.views.R
class InterstitialActivity : AppCompatActivity() {

    var ad: TapsellAd? = null

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // val Constraint = findViewById(R.id.Constraint) as ConstraintLayout 
        setContentView(R.layout.activity_interstitial)
//setContentView(Constraint)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initView()
    }

    private fun initView() {
        btnInterstitialBanner.setOnClickListener { requestInterstitialBannerAd(AdType.BANNER) }
        btnInterstitialVideo.setOnClickListener { requestInterstitialBannerAd(AdType.VIDEO) }
        btnShowAd.setOnClickListener { showAd() }
        btnShowAd.isEnabled = false
    }

    private fun requestInterstitialBannerAd(type: AdType) {
        val options = TapsellAdRequestOptions(CACHE_TYPE_STREAMED)
        Tapsell.requestAd(this@InterstitialActivity,
            if (type == AdType.BANNER) "610ed8bc35114c6ff3a596ee" else
                "610ecc7d260bc85635a14601", options,
            object : TapsellAdRequestListener() {
                override fun onAdAvailable(ad: TapsellAd?) {
                    if (isDestroyed)
                        return

                    this@InterstitialActivity.ad = ad
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
            it.show(this@InterstitialActivity, showOptions, object : TapsellAdShowListener() {
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

enum class AdType {
    BANNER, VIDEO
}
