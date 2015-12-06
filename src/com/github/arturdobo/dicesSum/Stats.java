package com.github.arturdobo.dicesSum;

import android.content.Context;
import android.content.SharedPreferences;

public class Stats {
	public static final String LVL_STATS = "playedGames";
	public static final String GAMES_NUMBER_ON_LVL = "gamesNumber";
	public static final String CORRECT_ANS = "correctAns";
	public static final String CORRECT_STREAK = "correctStreak";

	public static void update(Context context, boolean correct) {
		int correctAns = getCorrectAns(context);
		int correctStreak = getCorrectStreak(context);
		int gamesNumber = getGamesNumber(context) + 1;

		if (correct) {
			correctAns = correctAns + 1;
			correctStreak = correctStreak + 1;
		} else
			correctStreak = 0;

		putScore(getEditor(context), correctAns, correctStreak, gamesNumber);
	}

	public static void reset(Context context) {
		putScore(getEditor(context), 0, 0, 0);
	}

	private static SharedPreferences.Editor getEditor(Context context) {
		return getStatPrefs(context).edit();
	}

	private static SharedPreferences getStatPrefs(Context context) {
		return context.getSharedPreferences(LVL_STATS, Context.MODE_PRIVATE);
	}

	private static void putScore(SharedPreferences.Editor editor, int correctAns, int correctStreak, int gamesNumber) {
		editor.putInt(CORRECT_ANS, correctAns);
		editor.putInt(CORRECT_STREAK, correctStreak);
		editor.putInt(GAMES_NUMBER_ON_LVL, gamesNumber);
		editor.commit();
	}

	public static int getCorrectAns(Context context) {
		return getStatPrefs(context).getInt(CORRECT_ANS, 0);
	}

	public static int getGamesNumber(Context context) {
		return getStatPrefs(context).getInt(GAMES_NUMBER_ON_LVL, 0);
	}

	public static int getCorrectStreak(Context context) {
		return getStatPrefs(context).getInt(CORRECT_STREAK, 0);
	}

	public static int getCorrectnessPercentage(Context context) {
		return (int) ((getCorrectAns(context) * 1.0 / getGamesNumber(context)) * 100);
	}
}

