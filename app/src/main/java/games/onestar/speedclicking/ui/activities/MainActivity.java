package games.onestar.speedclicking.ui.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import games.onestar.speedclicking.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.body)
    ConstraintLayout body;

    @BindView(R.id.titleTextView)
    TextView titleTextView;

    @BindView(R.id.playButton)
    Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/8-bit-wonder.ttf");
        titleTextView.setTypeface(typeface);
        playButton.setTypeface(typeface);

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
    }

    @OnClick(R.id.playButton)
    void playButtonClick() {
        Intent intent = new Intent(this, TimeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

}
