package com.zeal.student.ui.view;

import com.zeal.student.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;


public class RibbonView extends LinearLayout {

	public RibbonView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View.inflate(context, R.layout.view_ribbon, this);
	}

}
