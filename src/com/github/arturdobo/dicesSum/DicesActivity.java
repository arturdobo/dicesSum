package com.github.arturdobo.dicesSum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class DicesActivity extends Activity {
	private int dicesNumber;
	private int timeToView;
	private int dicesSum;
	private Handler handler;
	private Runnable moveToResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dices);

		Bundle params = getIntent().getExtras();
		dicesNumber = params.getInt(Extras.DICES_NUMBER);
		timeToView = params.getInt(Extras.TIME_TO_VIEW);

		GridView gridView = (GridView) findViewById(R.id.dicesGridView);
		List<Integer> diceNumbers = generateNumbers();
		dicesSum = sum(diceNumbers);
		gridView.setAdapter(new DicesAdapter(this, R.id.dicesGridView, diceNumbers));

		handler = new Handler();
		moveToResult = new Runnable() {
			@Override
			public void run() {
				params.putInt(Extras.EXPECTED_DICES_SUM, dicesSum);

				Intent intent = new Intent(DicesActivity.this, ResultActivity.class);
				intent.putExtras(params);

				startActivity(intent);
			}
		};
		handler.postDelayed(moveToResult, timeToView * 1000);
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

	private static class DicesAdapter extends ArrayAdapter<Integer> {
		private static int[] diceIds = new int[] { R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4,
		                                           R.drawable.d5, R.drawable.d6 };

		private final Context context;
		private final List<Integer> diceNumbers;

		public DicesAdapter(Context context, int resource, List<Integer> diceNumbers) {
			super(context, resource, diceNumbers);
			this.diceNumbers = diceNumbers;
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getDice(diceNumbers.get(position));
		}

		private ImageView getDice(int number) {
			ImageView diceView = new ImageView(context);
			diceView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			diceView.setAdjustViewBounds(true);
			diceView.setBackgroundResource(diceIds[number]);
			diceView.setLayoutParams(new LinearLayout.LayoutParams(250, 250));

			return diceView;
		}
	}
}
