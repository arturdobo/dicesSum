package com.github.arturdobo.dicesSum;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

class DicesAdapter extends ArrayAdapter<Integer> {
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

		diceView.setLayoutParams(new RelativeLayout.LayoutParams(getDiceDimension(80),
		                                                         getDiceDimension(80)));

		return diceView;
	}

	private int getDiceDimension(int size) {
		float scale = getContext().getResources()
		                          .getDisplayMetrics().density;
		return (int) (size * scale + 0.5f);
	}
}
