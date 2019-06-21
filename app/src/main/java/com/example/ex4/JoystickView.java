package com.example.ex4;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class JoystickView extends SurfaceView implements View.OnTouchListener ,SurfaceHolder.Callback {
    private float centerX;
    private float centerY;
    private float baseRadius;
    private float hatRadius;
    private JoystickListener joystickListener;

    public void setDimensions(){
        centerX = (float)getWidth()/2;
        centerY = (float)getHeight()/2;
        baseRadius = (float)Math.min(getWidth(),getHeight())/3;
        hatRadius = (float)Math.min(getWidth(),getHeight())/10;
    }

    private void drawJoyStick(float posX, float posY){
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = this.getHolder().lockCanvas();
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            paint.setColor(Color.rgb(245, 245, 245));
            canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
            paint.setColor(Color.rgb(169, 169, 169));
            canvas.drawCircle(centerX,centerY, baseRadius, paint);
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(6);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(centerX,centerY, baseRadius, paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.rgb(250, 128, 114));
            canvas.drawCircle(posX, posY, hatRadius, paint);
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(6);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(posX, posY, hatRadius, paint);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public JoystickView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        // Global instance of JoystickListener
        if(context instanceof JoystickListener){
            joystickListener = (JoystickListener) context;
        }
    }
    public JoystickView(Context context, AttributeSet attributeSet, int style){
        super(context,attributeSet,style);
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }
    public JoystickView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setDimensions();
        drawJoyStick(centerX,centerY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.equals(this)){
            if(event.getAction() != event.ACTION_UP){
                float displacement = (float) Math.sqrt(Math.pow(event.getX() - centerX, 2) +
                        Math.pow(event.getY() - centerY, 2));
                if((displacement < baseRadius)) {
                    drawJoyStick(event.getX(),event.getY());

                    joystickListener.onJoystickMoved((event.getX() - centerX),
                            (event.getY() - centerY),getId(), baseRadius);
                } else {
                    float ratio = baseRadius/displacement;
                    float constrainedX = centerX + (event.getX() - centerX)*ratio;
                    float constrainedY = centerY + (event.getY() - centerY)*ratio;
                    drawJoyStick(constrainedX,constrainedY);
                    joystickListener.onJoystickMoved((constrainedX - centerX),
                            (constrainedY-centerY),getId(),baseRadius);
                }
            } else {
                // Reset the Joystick to its center
                drawJoyStick(centerX,centerY);
                joystickListener.onJoystickMoved(0,0,getId(),baseRadius);
            }
        }
        return true;
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh){
        setDimensions();
        drawJoyStick(centerX,centerY);
        this.invalidate();
        //super.onSizeChanged(w,h,oldw,oldh);
    }

    public interface JoystickListener
    {
        void onJoystickMoved(float xPercent, float yPercent, int source, float radius);
    }
}
