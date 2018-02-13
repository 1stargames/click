package games.onestar.speedclicking.ui.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import games.onestar.speedclicking.R
import java.util.*

class MainActivity : AppCompatActivity() {

    @BindView(R.id.body)
    internal var body: ConstraintLayout? = null

    @BindView(R.id.titleTextView)
    internal var titleTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        val eightBitTypeface = Typeface.createFromAsset(assets, "fonts/8-bit-wonder.ttf")
        titleTextView!!.typeface = eightBitTypeface

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
    }

    @OnClick(R.id.body)
    internal fun bodyClick() {
        val intent = Intent(this, TimeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
        finish()
    }

}
