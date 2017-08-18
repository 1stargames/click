package games.onestar.speedclicking.ui.activities;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import games.onestar.speedclicking.R;
import games.onestar.speedclicking.enums.GameState;

public class PlayActivity extends AppCompatActivity {

    @BindView(R.id.playAdView)
    AdView playAdView;

    @BindView(R.id.play_activity_body)
    ConstraintLayout body;

    @BindView(R.id.scoreTextView)
    TextView scoreTextView;

    @BindView(R.id.timeTextView)
    TextView timeTextView;

    private static GameState GAME_STATE = GameState.NOT_SET;
    private static int SCORE = 0;

    private static Boolean ORIENTATION_CHANGE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);

        if (GAME_STATE == GameState.NOT_SET && !ORIENTATION_CHANGE) {
            GAME_STATE = GameState.PLAYING;

            Typeface type = Typeface.createFromAsset(getAssets(), "fonts/8-bit-wonder.ttf");
            scoreTextView.setTypeface(type);
            scoreTextView.setText(String.valueOf(SCORE));

            timeTextView.setTypeface(type);
            Bundle bundle = getIntent().getExtras();
            int seconds = 0;
            if (bundle != null) {
                seconds = bundle.getInt("seconds");
                timeTextView.setText(String.valueOf(seconds));
            }

            // Set background color
            int[] androidColors = getResources().getIntArray(R.array.rainbowpastelcolors);
            int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
            body.setBackgroundColor(randomAndroidColor);

            new CountDownTimer(seconds * 1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    timeTextView.setText(String.valueOf(millisUntilFinished / 1000));
                }

                public void onFinish() {
                    GAME_STATE = GameState.FINISH;
                    timeTextView.setVisibility(View.INVISIBLE);
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
        SCORE = 0;
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
            SCORE++;
            scoreTextView.setText(String.valueOf(SCORE));
        }
    }

}
