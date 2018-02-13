package games.onestar.speedclicking.helper

import android.content.Context

object SavedScoreHelper {

    private val SAVED_SCORE = "SHARED_SCORE"
    private val TEN_SECONDS_HIGH_SCORE = "TEN_SECONDS_HIGH_SCORE"
    private val THIRTY_SECONDS_HIGH_SCORE = "THIRTY_SECONDS_HIGH_SCORE"
    private val SIXTY_SECONDS_HIGH_SCORE = "SIXTY_SECONDS_HIGH_SCORE"

    fun getTenSecondsHighScore(context: Context): Int {
        return getHighScore(context, TEN_SECONDS_HIGH_SCORE)
    }

    fun setTenSecondsHighScore(context: Context, score: Int) {
        setHighScore(context, TEN_SECONDS_HIGH_SCORE, score)
    }

    fun getThirtySecondsHighScore(context: Context): Int {
        return getHighScore(context, THIRTY_SECONDS_HIGH_SCORE)
    }

    fun setThirtySecondsHighScore(context: Context, score: Int) {
        setHighScore(context, THIRTY_SECONDS_HIGH_SCORE, score)
    }

    fun getSixtySecondsHighScore(context: Context): Int {
        return getHighScore(context, SIXTY_SECONDS_HIGH_SCORE)
    }

    fun setSixtySecondsHighScore(context: Context, score: Int) {
        setHighScore(context, SIXTY_SECONDS_HIGH_SCORE, score)
    }

    private fun getHighScore(context: Context, key: String): Int {
        val prefs = context.getSharedPreferences(SAVED_SCORE, Context.MODE_PRIVATE)
        return prefs.getInt(key, 0)
    }

    private fun setHighScore(context: Context, key: String, score: Int) {
        val prefs = context.getSharedPreferences(SAVED_SCORE, Context.MODE_PRIVATE)
        prefs.edit().putInt(key, score).apply()
    }

}
