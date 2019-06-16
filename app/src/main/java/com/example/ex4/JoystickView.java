package com.example.ex4;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class JoystickView extends View {

    private Paint background;
    private Paint circle;
    private Paint ellipse;

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
    }
}
