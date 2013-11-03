package com.zeal.student.ui.view;

import com.zeal.student.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;;


public class ChoicesView extends LinearLayout implements OnClickListener {

	public interface ChoiceSelectedListener {
		void onRightAnswer();
		void onWrongAnswer();
	}
	
	private ChoiceSelectedListener listener;
	
	public ChoicesView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View.inflate(context, R.layout.view_choices, this);
		findViewById(R.id.choice_1).setOnClickListener(this);
	}
	
	public void setChoiceSelectedListener(ChoiceSelectedListener listener) {
		this.listener = listener;
	}

	@Override
	public void onClick(View v) {
		if (listener != null) {
			if (v.getId() == R.id.choice_1) {
				listener.onRightAnswer();
			}
			else {
				listener.onWrongAnswer();
			}
		}
	}
	

}
