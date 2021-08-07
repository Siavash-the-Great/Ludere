package ir.tapsell.kotlinsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import ir.tapsell.sdk.*
import ir.tapsell.sdk.TapsellAdRequestOptions.CACHE_TYPE_STREAMED
import kotlinx.android.synthetic.main.activity_reward.*

class RewardActivity : AppCompatActivity() {

    var ad: TapsellAd? = null

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnRewardBanner.setOnClickListener {
            requestAd()
        }
    }

    private fun requestAd() {
        val options = TapsellAdRequestOptions(CACHE_TYPE_STREAMED)
        Tapsell.requestAd(this@RewardActivity, BuildConfig.TAPSELL_REWARDED_VIDEO, options,
            object : TapsellAdRequestListener {
                override fun onAdAvailable(ad: TapsellAd?) {
                    if (isDestroyed)
                        return

                    this@RewardActivity.ad = ad
                    showAd()
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
            showOptions.rotationMode = TapsellShowOptions.ROTATION_LOCKED_PORTRAIT
            it.show(this@RewardActivity, showOptions, object : TapsellAdShowListener {
                override fun onOpened(ad: TapsellAd) {
                    Log.e("RewardActivity", "on ad opened")
                }

                override fun onClosed(ad: TapsellAd) {
                    Log.e("RewardActivity", "on ad closed")
                }
            })
        } ?: run {
            Log.e("RewardActivity", "ad is not available")
        }

        ad = null
    }
}
