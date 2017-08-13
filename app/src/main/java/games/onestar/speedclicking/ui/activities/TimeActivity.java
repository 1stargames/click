package games.onestar.speedclicking.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import games.onestar.speedclicking.R;

public class TimeActivity extends AppCompatActivity {

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

        // Set background color
        int[] androidColors = getResources().getIntArray(R.array.rainbowpastelcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        body.setBackgroundColor(randomAndroidColor);
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
        overridePendingTransition(R.transition.activity_in, R.transition.activity_out);
    }

}
