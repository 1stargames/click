package games.onestar.speedclicking.ui.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/8-bit-wonder.ttf");
        titleTextView.setTypeface(type);

        // Set background color
        int[] androidColors = getResources().getIntArray(R.array.rainbowpastelcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        body.setBackgroundColor(randomAndroidColor);
    }

    @OnClick(R.id.playButton)
    void playButtonClick() {
        Intent intent = new Intent(this, TimeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.transition.activity_in, R.transition.activity_out);
    }

}
