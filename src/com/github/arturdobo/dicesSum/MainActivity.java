package com.github.arturdobo.dicesSum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends Activity {
	private Spinner dicesNumber;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		fillDicesNumberSpinner();
	}

	public void onStart(View view) {
		Keyboards.hide(view, this);

		String timeToView = ((EditText) findViewById(R.id.timeToShowText)).getText().toString();
		int time = timeToView.isEmpty() || "0".equals(timeToView) ? 1 : Integer.parseInt(timeToView);
		int number = Integer.parseInt(dicesNumber.getSelectedItem()
		                                         .toString());

		Bundle params = new Bundle();
		params.putInt(Extras.TIME_TO_VIEW, time);
		params.putInt(Extras.DICES_NUMBER, number);

		Intent intent = new Intent(this, DicesActivity.class);
		intent.putExtras(params);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	private void fillDicesNumberSpinner() {
		dicesNumber = (Spinner) findViewById(R.id.dicesNumberSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		                                                                     R.array.availableDicesNumbers,
		                                                                     android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dicesNumber.setAdapter(adapter);
	}

}
