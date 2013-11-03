package com.zeal.student;

import com.zeal.student.ui.view.*;
import com.zeal.student.ui.view.ChoicesView.ChoiceSelectedListener;
import com.zeal.student.ui.view.ScratchPadView.DrawMode;
import com.zeal.student.util.SystemUiHider;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class QuestionActivity extends Activity implements ChoiceSelectedListener {

	private ScratchPadView scratchPad;
	private LockableScrollView scroll;
	private ChoicesView choices;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_teal));
		getActionBar().setTitle("Compose Shapes");
		setContentView(R.layout.activity_question);
		scratchPad = (ScratchPadView)findViewById(R.id.scratch_pad);
		scratchPad.setDrawMode(DrawMode.RENDER);
		scroll = (LockableScrollView)findViewById(R.id.scroll);
		choices = (ChoicesView)findViewById(R.id.choices);
		choices.setChoiceSelectedListener(this);
	}
	
	

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		if (scratchPad.getDrawMode()==DrawMode.RECORD)
			getMenuInflater().inflate(R.menu.question_draw_on, menu);
		else
			getMenuInflater().inflate(R.menu.question_draw_off, menu);
		MenuItem item = menu.add("Zealot");
		item.setIcon(R.drawable.zealot);
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onPrepareOptionsMenu(menu);
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId()==R.id.draw_toggle) {
			DrawMode mode = scratchPad.getDrawMode();
			if (DrawMode.RENDER == mode)
				mode = DrawMode.RECORD;
			else
				mode = DrawMode.RENDER;
			scratchPad.setDrawMode(mode);
			scroll.setScrollingEnabled(!scroll.isScrollable());
		}
		invalidateOptionsMenu();
		return super.onOptionsItemSelected(item);
	}



	@Override
	public void onRightAnswer() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onWrongAnswer() {
		// TODO Auto-generated method stub
		
	}
	
}
