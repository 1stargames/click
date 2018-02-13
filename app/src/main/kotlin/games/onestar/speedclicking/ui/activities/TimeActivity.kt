package games.onestar.speedclicking.ui.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import games.onestar.speedclicking.R
import java.util.*

class TimeActivity : AppCompatActivity() {

    @BindView(R.id.timeAdView)
    internal var timeAdView: AdView? = null

    @BindView(R.id.time_activity_body)
    internal var body: ConstraintLayout? = null

    @BindView(R.id.tenSecButton)
    internal var tenSecButton: Button? = null

    @BindView(R.id.thirtySecButton)
    internal var thirtySecButton: Button? = null

    @BindView(R.id.sixtySecButton)
    internal var sixtySecButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time)
        ButterKnife.bind(this)

        val typeface = Typeface.createFromAsset(assets, "fonts/8-bit-wonder.ttf")
        tenSecButton!!.typeface = typeface
        thirtySecButton!!.typeface = typeface
        sixtySecButton!!.typeface = typeface

        // Set background color
        val androidColors = resources.getIntArray(R.array.rainbowpastelcolors)
        val randomAndroidColor = androidColors[Random().nextInt(androidColors.size)]
        body!!.setBackgroundColor(randomAndroidColor)

        // Set full screen
        var uiOptions = this.window.decorView.systemUiVisibility
        uiOptions = uiOptions xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            uiOptions = uiOptions xor View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            uiOptions = uiOptions xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
        val decorView = window.decorView
        decorView.systemUiVisibility = uiOptions

        // Set ads
        val adRequest = AdRequest.Builder().build()
        timeAdView!!.loadAd(adRequest)
    }

    @OnClick(R.id.tenSecButton)
    internal fun tenSecButtonClick() {
        startPlayActivity(10)
    }

    @OnClick(R.id.thirtySecButton)
    internal fun thirtySecButtonClick() {
        startPlayActivity(30)
    }

    @OnClick(R.id.sixtySecButton)
    internal fun sixtySecButtonClick() {
        startPlayActivity(60)
    }

    private fun startPlayActivity(seconds: Int) {
        val intent = Intent(this, PlayActivity::class.java)
        val bundle = Bundle()
        bundle.putInt("seconds", seconds)
        intent.putExtras(bundle)
        startActivity(intent)
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
        finish()
    }

}
