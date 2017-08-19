package games.onestar.speedclicking.ui.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import games.onestar.speedclicking.R;
import games.onestar.speedclicking.enums.GameState;
import games.onestar.speedclicking.helper.SavedScoreHelper;

public class PlayActivity extends AppCompatActivity {

    private static final String TAG = "PlayActivity";

    @BindView(R.id.playAdView)
    AdView playAdView;

    @BindView(R.id.play_activity_body)
    ConstraintLayout body;

    @BindView(R.id.scoreTextView)
    TextView scoreTextView;

    @BindView(R.id.timeTextView)
    TextView timeTextView;

    @BindView(R.id.newHighScoreTextView)
    TextView newHighScoreTextView;

    @BindView(R.id.endGameButton)
    Button endGameTextView;

    private static GameState GAME_STATE = GameState.NOT_SET;
    private static Boolean ORIENTATION_CHANGE = false;

    private int gameType = 0;
    private static int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);

        if (GAME_STATE == GameState.NOT_SET && !ORIENTATION_CHANGE) {
            GAME_STATE = GameState.PLAYING;

            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/8-bit-wonder.ttf");
            scoreTextView.setTypeface(typeface);
            timeTextView.setTypeface(typeface);
            newHighScoreTextView.setTypeface(typeface);
            endGameTextView.setTypeface(typeface);

            scoreTextView.setText(String.valueOf(score));

            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                gameType = bundle.getInt("seconds");
                timeTextView.setText(String.valueOf(gameType));
            }

            // Set background color
            int[] androidColors = getResources().getIntArray(R.array.rainbowpastelcolors);
            int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
            body.setBackgroundColor(randomAndroidColor);

            new CountDownTimer(gameType * 1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    timeTextView.setText(String.valueOf(millisUntilFinished / 1000));
                }

                public void onFinish() {
                    handleEndGame();
                }

            }.start();

            // Set full screen
            int uiOptions = this.getWindow().getDecorView().getSystemUiVisibility();
            uiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            // Status bar hiding: Backwards compatible to Jellybean
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                uiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                uiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(uiOptions);

            // Set ads
            AdRequest adRequest = new AdRequest.Builder().build();
            playAdView.loadAd(adRequest);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        GAME_STATE = GameState.NOT_SET;
        score = 0;
        ORIENTATION_CHANGE = false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ORIENTATION_CHANGE = true;
    }

    @OnClick(R.id.play_activity_body)
    void click() {
        if (GAME_STATE == GameState.PLAYING) {
            score++;
            scoreTextView.setText(String.valueOf(score));
        }
    }

    @OnClick(R.id.endGameButton)
    void endGameButtonClick() {
        Intent intent = new Intent(this, TimeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    private void handleEndGame() {
        GAME_STATE = GameState.FINISH;
        timeTextView.setVisibility(View.INVISIBLE);

        // get saved high score
        int highScore = 0;
        switch (gameType) {
            case 10:
                highScore = SavedScoreHelper.getTenSecondsHighScore(this);
                break;
            case 30:
                highScore = SavedScoreHelper.getThirtySecondsHighScore(this);
                break;
            case 60:
                highScore = SavedScoreHelper.getSixtySecondsHighScore(this);
                break;
        }

        // update high score
        if (score > highScore) {
            Log.d(TAG, "It's a new high score! GameType: " + gameType + "seconds, old high score: " + highScore + ", new high score: " + score);
            newHighScoreTextView.setVisibility(View.VISIBLE);
            newHighScoreTextView.startAnimation((Animation) AnimationUtils.loadAnimation(this, R.anim.high_score));
            switch (gameType) {
                case 10:
                    SavedScoreHelper.setTenSecondsHighScore(this, score);
                    break;
                case 30:
                    SavedScoreHelper.setThirtySecondsHighScore(this, score);
                    break;
                case 60:
                    SavedScoreHelper.setSixtySecondsHighScore(this, score);
                    break;
            }
        }

        // display end screen
        endGameTextView.setVisibility(View.VISIBLE);
    }

}
