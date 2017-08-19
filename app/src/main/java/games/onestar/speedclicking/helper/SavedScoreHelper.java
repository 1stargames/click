package games.onestar.speedclicking.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SavedScoreHelper {

    private static String SAVED_SCORE = "SHARED_SCORE";
    private static String TEN_SECONDS_HIGH_SCORE = "TEN_SECONDS_HIGH_SCORE";
    private static String THIRTY_SECONDS_HIGH_SCORE = "THIRTY_SECONDS_HIGH_SCORE";
    private static String SIXTY_SECONDS_HIGH_SCORE = "SIXTY_SECONDS_HIGH_SCORE";

    public static int getTenSecondsHighScore(Context context) {
        return getHighScore(context, TEN_SECONDS_HIGH_SCORE);
    }

    public static void setTenSecondsHighScore(Context context, int score) {
        setHighScore(context, TEN_SECONDS_HIGH_SCORE, score);
    }

    public static int getThirtySecondsHighScore(Context context) {
        return getHighScore(context, THIRTY_SECONDS_HIGH_SCORE);
    }

    public static void setThirtySecondsHighScore(Context context, int score) {
        setHighScore(context, THIRTY_SECONDS_HIGH_SCORE, score);
    }

    public static int getSixtySecondsHighScore(Context context) {
        return getHighScore(context, SIXTY_SECONDS_HIGH_SCORE);
    }

    public static void setSixtySecondsHighScore(Context context, int score) {
        setHighScore(context, SIXTY_SECONDS_HIGH_SCORE, score);
    }

    private static int getHighScore(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(SAVED_SCORE, Context.MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }

    private static void setHighScore(Context context, String key, int score) {
        SharedPreferences prefs = context.getSharedPreferences(SAVED_SCORE, Context.MODE_PRIVATE);
        prefs.edit().putInt(key, score).apply();
    }

}
