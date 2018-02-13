package games.onestar.speedclicking.ui.components

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import android.view.View
import android.widget.TextView

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import games.onestar.speedclicking.ui.activities.TimeActivity
import games.onestar.speedclicking.R

class ButtonsLineLayout : LinearLayoutCompat {

    companion object {
        private val GITHUB_LINK = "https://github.com/1stargames/speed_clicking"
    }

    @BindView(R.id.github_button)
    internal var githubButton: TextView? = null

    @BindView(R.id.play_button)
    internal var playButton: TextView? = null

    @BindView(R.id.trophy_button)
    internal var trophyButton: TextView? = null

    @BindView(R.id.settings_button)
    internal var settingsButton: TextView? = null

    @BindView(R.id.rate_button)
    internal var rateButton: TextView? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.buttons_line, this)
        ButterKnife.bind(this)

        val fontawesomeTypeface = Typeface.createFromAsset(context.assets, "fonts/fontawesome.ttf")
        githubButton!!.typeface = fontawesomeTypeface
        playButton!!.typeface = fontawesomeTypeface
        trophyButton!!.typeface = fontawesomeTypeface
        settingsButton!!.typeface = fontawesomeTypeface
        rateButton!!.typeface = fontawesomeTypeface
    }

    @OnClick(R.id.github_button)
    internal fun githubButtonClick() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_LINK))
        context.startActivity(browserIntent)
        (context as Activity).finish()
    }

    @OnClick(R.id.play_button)
    internal fun playButtonCLick() {
        val intent = Intent(context, TimeActivity::class.java)
        context.startActivity(intent)
        (context as Activity).overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
        (context as Activity).finish()
    }

    @OnClick(R.id.trophy_button)
    internal fun trophyButtonCLick() {
    }

    @OnClick(R.id.settings_button)
    internal fun settingsButtonClick() {
    }

    @OnClick(R.id.rate_button)
    internal fun rateButtonClick() {
        val uri = Uri.parse("market://details?id=" + context.packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
        }
        try {
            context.startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + context.packageName)))
        }

    }

}
