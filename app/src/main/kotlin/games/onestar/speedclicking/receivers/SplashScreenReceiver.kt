package games.onestar.speedclicking.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import games.onestar.speedclicking.ui.activities.MainActivity

class SplashScreenReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val i = Intent(context, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(i)
    }

}
