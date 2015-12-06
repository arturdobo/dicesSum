package com.github.arturdobo.dicesSum;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ResultActivity extends Activity {

	private EditText sumEditText;
	private Bundle params;
	private TextView resultTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);

		params = getIntent().getExtras();

		sumEditText = (EditText) findViewById(R.id.checkSumEditText);
		sumEditText.setOnFocusChangeListener(showKeyboard());

		resultTextView = (TextView) findViewById(R.id.resultTextView);
	}

	public void onCheckSum(View view) {
		Keyboards.hide(view, this);

		String resultStr = sumEditText.getText().toString();
		int given = Integer.parseInt(resultStr.isEmpty() ? "0" : resultStr);
		int expected = params.getInt(Extras.EXPECTED_DICES_SUM);
		Resources res = getResources();

		if (given == expected) {
			resultTextView.setText(res.getString(R.string.correctAnswer));
			resultTextView.setTextColor(Color.GREEN);
		} else {
			resultTextView.setText(String.format(res.getString(R.string.wrongAnswer), expected));
			resultTextView.setTextColor(Color.RED);
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void onOnceAgain(View view) {
		Keyboards.hide(view, this);

		params.remove(Extras.EXPECTED_DICES_SUM);
		Intent intent = new Intent(this, DicesActivity.class);
		intent.putExtras(params);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		reset();
	}

	public void onSettings(View view) {
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
					Keyboards.hide(v, ResultActivity.this);
			}
		};
	}
}
