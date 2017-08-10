package games.onestar.click.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import games.onestar.click.MainActivity;

public class SplashScreenReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}
