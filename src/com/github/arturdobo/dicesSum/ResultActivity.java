package com.github.arturdobo.dicesSum;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends Activity {
	private static Map<Boolean, Integer> colors = new HashMap<>(2);
	private Resources res;

	static {
		colors.put(true, Color.GREEN);
		colors.put(true, Color.RED);
	}

	private EditText sumEditText;
	private Bundle params;
	private TextView resultTextView;
	private boolean sumChecked = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);

		params = getIntent().getExtras();

		sumEditText = (EditText) findViewById(R.id.checkSumEditText);
		resultTextView = (TextView) findViewById(R.id.resultTextView);
		res = getResources();
	}

	public void onCheckSum(View view) {
		Keyboards.hide(view, this);

		String givenStr = sumEditText.getText().toString();
		int given = Integer.parseInt(givenStr.isEmpty() ? "0" : givenStr);
		int expected = params.getInt(Keys.EXPECTED_DICES_SUM);

		boolean correct = given == expected;
		if (correct)
			resultTextView.setText(res.getString(R.string.correct));
		else
			resultTextView.setText(String.format(res.getString(R.string.wrongAnswer),
			                                     expected,
			                                     Stats.getCorrectStreak(this)));

		resultTextView.setTextColor(colors.get(correct));

		updateStats(correct);
		sumChecked = true;
	}

	private void updateStats(boolean correct) {
		Stats.update(this, correct);
		TextView correctness = (TextView) findViewById(R.id.correctnessPercentageEditText);
		correctness.setText(String.format(res.getString(R.string.correctnessStats),
		                                  Stats.getCorrectnessPercentage(this),
		                                  Stats.getCorrectStreak(this)));
	}

	public void onNext(View view) {
		if (!sumChecked) onCheckSum(view);

		params.remove(Keys.EXPECTED_DICES_SUM);
		Intent intent = new Intent(this, DicesActivity.class);
		intent.putExtras(params);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		reset();
	}

	public void onReset(View view) {
		reset();
	}

	private void reset() {
		startActivity(new Intent(this, MainActivity.class));
	}

}
