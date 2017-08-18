package games.onestar.speedclicking.ui.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import games.onestar.speedclicking.R;

public class TimeActivity extends AppCompatActivity {

    @BindView(R.id.timeAdView)
    AdView timeAdView;

    @BindView(R.id.time_activity_body)
    ConstraintLayout body;

    @BindView(R.id.tenSecButton)
    Button tenSecButton;

    @BindView(R.id.thirtySecButton)
    Button thirtySecButton;

    @BindView(R.id.sixtySecButton)
    Button sixtySecButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        ButterKnife.bind(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/8-bit-wonder.ttf");
        tenSecButton.setTypeface(typeface);
        thirtySecButton.setTypeface(typeface);
        sixtySecButton.setTypeface(typeface);

        // Set background color
        int[] androidColors = getResources().getIntArray(R.array.rainbowpastelcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        body.setBackgroundColor(randomAndroidColor);

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
        timeAdView.loadAd(adRequest);
    }

    @OnClick(R.id.tenSecButton)
    void tenSecButtonClick() {
        startPlayActivity(10);
    }

    @OnClick(R.id.thirtySecButton)
    void thirtySecButtonClick() {
        startPlayActivity(30);
    }

    @OnClick(R.id.sixtySecButton)
    void sixtySecButtonClick() {
        startPlayActivity(60);
    }

    private void startPlayActivity(int seconds) {
        Intent intent = new Intent(this, PlayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("seconds", seconds);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

}
