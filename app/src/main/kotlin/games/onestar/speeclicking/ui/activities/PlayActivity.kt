package games.onestar.speeclicking.ui.activities

import android.content.res.Configuration
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutCompat
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import games.onestar.speeclicking.enums.GameState
import games.onestar.speeclicking.helper.SavedScoreHelper
import games.onestar.speedclicking.R
import java.util.*

class PlayActivity : AppCompatActivity() {

    companion object {
        private val TAG = "PlayActivity"
        private var GAME_STATE = GameState.NOT_SET
        private var ORIENTATION_CHANGE: Boolean? = false
        private var score = 0
    }

    @BindView(R.id.playAdView)
    internal var playAdView: AdView? = null

    @BindView(R.id.play_activity_body)
    internal var body: ConstraintLayout? = null

    @BindView(R.id.scoreTextView)
    internal var scoreTextView: TextView? = null

    @BindView(R.id.timeTextView)
    internal var timeTextView: TextView? = null

    @BindView(R.id.newHighScoreTextView)
    internal var newHighScoreTextView: TextView? = null

    @BindView(R.id.buttons_line)
    internal var buttonLines: LinearLayoutCompat? = null

    private var gameType = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayout()

        if (GAME_STATE === GameState.NOT_SET && (!ORIENTATION_CHANGE!!)!!) {
            GAME_STATE = GameState.PLAYING

            val bundle = intent.extras
            if (bundle != null) {
                gameType = bundle.getInt("seconds")
                timeTextView!!.text = gameType.toString()
            }

            object : CountDownTimer((gameType * 1000).toLong(), 1000) {

                override fun onTick(millisUntilFinished: Long) {
                    timeTextView!!.text = (millisUntilFinished / 1000).toString()
                }

                override fun onFinish() {
                    handleEndGame()
                }

            }.start()
        }
    }

    private fun setLayout() {
        setContentView(R.layout.activity_play)
        ButterKnife.bind(this)
        val eightBitTypeface = Typeface.createFromAsset(assets, "fonts/8-bit-wonder.ttf")
        scoreTextView!!.typeface = eightBitTypeface
        timeTextView!!.typeface = eightBitTypeface
        newHighScoreTextView!!.typeface = eightBitTypeface

        scoreTextView!!.text = score.toString()

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
        playAdView!!.loadAd(adRequest)
    }

    override fun onPause() {
        super.onPause()
        GAME_STATE = GameState.NOT_SET
        score = 0
        ORIENTATION_CHANGE = false
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        ORIENTATION_CHANGE = true
        setLayout()
        if (GAME_STATE === GameState.FINISH) {
            timeTextView!!.visibility = View.INVISIBLE
            buttonLines!!.visibility = View.VISIBLE
        }
    }

    @OnClick(R.id.play_activity_body)
    internal fun click() {
        if (GAME_STATE === GameState.PLAYING) {
            score++
            scoreTextView!!.text = score.toString()
        }
    }

    private fun handleEndGame() {
        GAME_STATE = GameState.FINISH
        timeTextView!!.visibility = View.INVISIBLE

        // get saved high score
        var highScore = 0
        when (gameType) {
            10 -> highScore = SavedScoreHelper.getTenSecondsHighScore(this)
            30 -> highScore = SavedScoreHelper.getThirtySecondsHighScore(this)
            60 -> highScore = SavedScoreHelper.getSixtySecondsHighScore(this)
        }

        // update high score
        if (score > highScore) {
            Log.d(TAG, "It's a new high score! GameType: " + gameType + "seconds, old high score: " + highScore + ", new high score: " + score)
            newHighScoreTextView!!.visibility = View.VISIBLE
            newHighScoreTextView!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.high_score) as Animation)
            when (gameType) {
                10 -> SavedScoreHelper.setTenSecondsHighScore(this, score)
                30 -> SavedScoreHelper.setThirtySecondsHighScore(this, score)
                60 -> SavedScoreHelper.setSixtySecondsHighScore(this, score)
            }
        }

        // display end screen
        buttonLines!!.visibility = View.VISIBLE
    }

}
