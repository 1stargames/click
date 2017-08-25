package games.onestar.speedclicking.ui.components;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import games.onestar.speedclicking.R;
import games.onestar.speedclicking.ui.activities.TimeActivity;

public class ButtonsLineLayout extends LinearLayoutCompat {

    private static final String GITHUB_LINK = "https://github.com/1stargames/speed_clicking";

    @BindView(R.id.github_button)
    TextView githubButton;

    @BindView(R.id.play_button)
    TextView playButton;

    @BindView(R.id.trophy_button)
    TextView trophyButton;

    @BindView(R.id.settings_button)
    TextView settingsButton;

    @BindView(R.id.rate_button)
    TextView rateButton;

    public ButtonsLineLayout(Context context) {
        super(context);
        init();
    }

    public ButtonsLineLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ButtonsLineLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.buttons_line, this);
        ButterKnife.bind(this);

        Typeface fontawesomeTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/fontawesome.ttf");
        githubButton.setTypeface(fontawesomeTypeface);
        playButton.setTypeface(fontawesomeTypeface);
        trophyButton.setTypeface(fontawesomeTypeface);
        settingsButton.setTypeface(fontawesomeTypeface);
        rateButton.setTypeface(fontawesomeTypeface);
    }

    @OnClick(R.id.github_button)
    void githubButtonClick() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_LINK));
        getContext().startActivity(browserIntent);
    }

    @OnClick(R.id.play_button)
    void playButtonCLick() {
        Intent intent = new Intent(getContext(), TimeActivity.class);
        getContext().startActivity(intent);
        ((Activity) getContext()).overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    @OnClick(R.id.trophy_button)
    void trophyButtonCLick() {
    }

    @OnClick(R.id.settings_button)
    void settingsButtonClick() {
    }

    @OnClick(R.id.rate_button)
    void rateButtonClick() {
        Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        }
        try {
            getContext().startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())));
        }
    }

}
