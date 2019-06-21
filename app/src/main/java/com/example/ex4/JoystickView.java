package com.example.ex4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class JoystickView extends SurfaceView implements View.OnTouchListener ,SurfaceHolder.Callback {
    // Data members that holds the center of the screen
    private float centerX;
    private float centerY;
    // The radius of the joystick movement
    private float baseRadius;
    // The radius of the joystick hat
    private float hatRadius;
    // Listener to the joystick
    private JoystickListener joystickListener;

    /**
     * The function setDimensions() - set the data members,
     * According to the screen Dimensions.
     */
    public void setDimensions(){
        centerX = (float)getWidth()/2;
        centerY = (float)getHeight()/2;
        baseRadius = (float)Math.min(getWidth(),getHeight())/3;
        hatRadius = (float)Math.min(getWidth(),getHeight())/10;
    }

    /**
     * The function drawJoyStick - draw the joystick on the screen using canvas,
     * According to the given parameters.
     * @param posX - the position of the joystick head in the x axis.
     * @param posY - the position of the joystick head in the y axis.
     */
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

    /**
     * Constructor
     */
    public JoystickView(Context context) {
        super(context);
        getHolder().addCallback(this);
        // SurfaceView will use on touch method when touch event happen
        setOnTouchListener(this);
        // Global instance of JoystickListener
        if(context instanceof JoystickListener){
            joystickListener = (JoystickListener) context;
        }
    }

    /**
     * The function surfaceCreated - set the dimensions of the screen when the surface create,
     * And draw the joystick.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setDimensions();
        drawJoyStick(centerX,centerY);
    }

    /*
     * next to functions make sure that orientation changes to not affect the
     * appearance of the screen by redrawing it based on new dimensions values.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        setDimensions();
        drawJoyStick(centerX,centerY);
    }

    /**
     * The function onSizeChanged - is called when the size of the screen changed,
     * when the phone rotate. It sets the screen dimension and redraw the joystick.
     */
    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w,h,oldw,oldh);
        setDimensions();
        drawJoyStick(centerX,centerY);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) { }

    /**
     * The function onTouch - is called when the user touched the screen,
     * It checks the location of the touche, and normalize it according to the
     * Joystick range. It also notify the listener for change,
     * And send the distance of the touch from the center of the screen.
     *
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.equals(this)){
            // Checks that the user is still touch the screen
            if(event.getAction() != MotionEvent.ACTION_UP){
                // Calculate the distance of the touch from the center using pitagoras
                float displacement = (float) Math.sqrt(Math.pow(event.getX() - centerX, 2) +
                        Math.pow(event.getY() - centerY, 2));
                // Checks if the distance is inside the range
                if((displacement < baseRadius)) {
                    // Draw the new joystick location
                    drawJoyStick(event.getX(),event.getY());
                    joystickListener.onJoystickMoved((event.getX() - centerX),
                            (event.getY() - centerY),getId(), baseRadius);
                } else {
                    /* In case that the touch is outside the range,
                       We set the joystick to the appropriate place inside the range.
                     */
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

    /**
     * Joystick listener interface.
     * The onJoystickMoved function called when the joystick changed its position.
     */
    public interface JoystickListener {
        void onJoystickMoved(float xPercent, float yPercent, int source, float radius);
    }
}
