package com.zeal.student.ui.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ScratchPadView extends View implements OnTouchListener {
    public enum DrawMode { IDLE, RENDER, RECORD, PLAYBACK};
    private DrawMode drawMode = DrawMode.IDLE;
    List<Point> points = new ArrayList<Point>();
    Paint paint = new Paint();
    /**
     * Timestamp of when playback was started
     */
    long playbackTs;
    /**
     * Timestamp of when recording started
     */
    long recordTs;


    public ScratchPadView(Context context, AttributeSet attr) {
        super(context, attr);
        setFocusable(true);
        setFocusableInTouchMode(true);

        this.setOnTouchListener(this);

        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
    }
    
    public DrawMode getDrawMode() {
    	return drawMode;
    }
    
    public void clear() {
    	segments.clear();
    	invalidate();
    }
    
    public void setDrawMode(DrawMode drawMode) {
    	this.drawMode = drawMode;
    	if (drawMode == DrawMode.PLAYBACK) {
    		playbackTs = System.currentTimeMillis();
    		invalidate();
    	}
    	else if (drawMode == DrawMode.RECORD) {
    		recordTs = System.currentTimeMillis();
//    		segments.clear();
    		invalidate();
    	}
    }
    

    @Override
    public void onDraw(Canvas canvas) {
    	switch (drawMode) {
    	case RECORD:
	    	for (Segment segment : segments) {
	    		segment.draw(canvas, paint);
	    	}
    		break;
    	case PLAYBACK:
    		for (Segment segment : segments) {
    			if (!segment.draw(recordTs, playbackTs, canvas, paint)) {
    				this.postInvalidateDelayed(50);
    				break;
    			}
    		}
    		break;
    	case RENDER:
    		for (Segment segment : segments) {
    			segment.draw(recordTs, playbackTs, canvas, paint);
    		}
    		break;
    	}
    }
    
    private static class DrawPoint extends Point {
    	
    	long timeStamp;
    	public DrawPoint(int x, int y) {
    		super(x,y);
    		timeStamp = System.currentTimeMillis();
    	}
    	
    	public long getTimeStamp() {
    		return timeStamp;
    	}
    }

    private static class Segment extends ArrayList<DrawPoint > {
    	public Segment(DrawPoint start) {
    		this.add(start);
    	}
    	
    	public void draw(Canvas canvas, Paint paint) {
    		Point lastPoint = null;
    		for (Point point : this) {
    			if (lastPoint == null)
    				lastPoint = point;
    			else {
    				canvas.drawLine(lastPoint.x, lastPoint.y, point.x, point.y, paint);
    				lastPoint = point;
    			}
    		}
    	}
    	
    	public boolean draw(long firstPointTs, long playbackTs, Canvas canvas, Paint paint) {
    		DrawPoint lastPoint = null;
    		for (DrawPoint point : this) {
    			if (lastPoint == null)
    				lastPoint = point;
    			else if (System.currentTimeMillis() - playbackTs > point.getTimeStamp() - firstPointTs) {
    				canvas.drawLine(lastPoint.x, lastPoint.y, point.x, point.y, paint);
    				lastPoint = point;
    			}
    			else
    				return false;
    		}
    		return true;
    	}
    }
    
    private Segment currentSegment;
    private ArrayList<Segment> segments = new ArrayList<Segment>();
    
    public boolean onTouch(View view, MotionEvent event) {
    	if (drawMode == DrawMode.RECORD) { 
	    	if (event.getAction() == MotionEvent.ACTION_DOWN) {
	    		currentSegment = new Segment(new DrawPoint((int)event.getX(), (int)event.getY()));
	    		segments.add(currentSegment);
	    	}
	    	else if (event.getAction() == MotionEvent.ACTION_MOVE) {
	    		currentSegment.add(new DrawPoint((int)event.getX(), (int)event.getY()));
	    	}
    	}
        invalidate();
        return true;
    }
}

