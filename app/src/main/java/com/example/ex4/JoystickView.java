package com.example.ex4;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class JoystickView extends View {
    private Paint background;
    private Paint circle;
    private Paint ellipse;
    private int windowWidth;
    private int windowHeight;

    public JoystickView(Context context) {
        super(context);
        background=new Paint(Paint.ANTI_ALIAS_FLAG);
        background.setColor(Color.rgb(201,185,185));
        background.setStyle(Paint.Style.FILL);
        circle=new Paint(Paint.ANTI_ALIAS_FLAG);
        circle.setColor(Color.rgb(135,131,131));
        circle.setStyle(Paint.Style.FILL);
        ellipse=new Paint(Paint.ANTI_ALIAS_FLAG);
        ellipse.setColor(Color.rgb(226,87,87));
        ellipse.setStyle(Paint.Style.FILL);
        windowHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        windowWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    @Override
    protected void onDraw(Canvas canvas){
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(metrics);
        float scale = metrics.densityDpi;
        canvas.drawRect( metrics.xdpi-20*scale, metrics.ydpi-20*scale,
                (metrics.xdpi+metrics.widthPixels)+20*scale,
                metrics.ydpi+metrics.heightPixels, background);
        RectF rectF = new RectF(metrics.xdpi,metrics.ydpi,
                metrics.xdpi+metrics.widthPixels,metrics.ydpi+metrics.heightPixels);
     canvas.drawOval(rectF,circle);
    }
}
