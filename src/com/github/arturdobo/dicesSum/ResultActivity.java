package com.github.arturdobo.dicesSum;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ResultActivity extends Activity {

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

		Keyboards.show(this);
	}

	public void onCheckSum(View view) {
		Keyboards.hide(view, this);

		String resultStr = sumEditText.getText().toString();
		int given = Integer.parseInt(resultStr.isEmpty() ? "0" : resultStr);
		int expected = params.getInt(Keys.EXPECTED_DICES_SUM);
		Resources res = getResources();

		boolean correct = given == expected;
		if (correct) {
			resultTextView.setText(res.getString(R.string.correct));
			resultTextView.setTextColor(Color.GREEN);
		} else {
			resultTextView.setText(String.format(res.getString(R.string.wrongAnswer),
			                                     expected,
			                                     Stats.getCorrectStreak(this)));
			resultTextView.setTextColor(Color.RED);
		}

		updateStats(correct);
		sumChecked = true;
	}

	private void updateStats(boolean correct) {
		Stats.update(this, correct);
		Resources res = getResources();
		TextView correctness = (TextView) findViewById(R.id.correctnessPercentageEditText);
		correctness.setText(String.format(res.getString(R.string.correctnessStats),
		                                  Stats.getCorrectnessPercentage(this),
		                                  Stats.getCorrectStreak(this)));
	}

	public void onNext(View view) {
		Keyboards.hide(view, this);

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

	private View.OnFocusChangeListener showKeyboard() {
		return new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus)
					Keyboards.show(ResultActivity.this);
			}
		};
	}
}
