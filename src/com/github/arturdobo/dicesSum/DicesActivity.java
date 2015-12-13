package com.github.arturdobo.dicesSum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class DicesActivity extends Activity {
	private int dicesNumber;
	private float timeToView;
	private int dicesSum;
	private Handler handler;
	private Runnable moveToResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dices);

		Bundle params = getIntent().getExtras();
		dicesNumber = params.getInt(Keys.DICES_NUMBER);
		timeToView = params.getFloat(Keys.TIME_TO_VIEW);

		GridView gridView = (GridView) findViewById(R.id.dicesGridView);
		List<Integer> diceNumbers = generateNumbers();
		dicesSum = sum(diceNumbers);
		gridView.setAdapter(new DicesAdapter(this, R.id.dicesGridView, diceNumbers));

		handler = new Handler();
		moveToResult = new Runnable() {
			@Override
			public void run() {
				params.putInt(Keys.EXPECTED_DICES_SUM, dicesSum);

				Intent intent = new Intent(DicesActivity.this, ResultActivity.class);
				intent.putExtras(params);

				startActivity(intent);
			}
		};
		handler.postDelayed(moveToResult, (long) (timeToView * 1000));
	}

	@Override
	public void onBackPressed() {
		handler.removeCallbacks(moveToResult);
		startActivity(new Intent(this, MainActivity.class));
	}

	private int sum(List<Integer> diceNumbers) {
		int sum = 0;
		for (Integer diceNumber : diceNumbers) {
			sum += diceNumber + 1;
		}
		return sum;
	}

	private List<Integer> generateNumbers() {
		List<Integer> numbers = new ArrayList<>();

		for (int i = 0; i < dicesNumber; i++) {
			numbers.add((int) Math.round(Math.random() * 5));
		}

		return numbers;
	}

}
