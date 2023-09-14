package com.girlsskinsminecraft.boyskinsminecraft.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;


public class GridRelativeLayout extends RelativeLayout {
    private static final int CELL_COLOR = -1644826;
    private static final int CELL_LINE_WIDTH = 4;
    private static final int CELL_SIZE = 74;
    private static final int START_X = -48;
    private static final int START_Y = 0;
    private final Paint paint;

    public GridRelativeLayout(Context context) {
        super(context);
        this.paint = new Paint();
        init();
    }

    public GridRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.paint = new Paint();
        init();
    }

    public GridRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.paint = new Paint();
        init();
    }

    private void init() {
        setWillNotDraw(false);
        this.paint.setColor(CELL_COLOR);
        this.paint.setStrokeWidth(4.0f);
    }

    @Override 
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        canvas.drawARGB(255, 255, 255, 255);
        for (int i = START_X; i < measuredWidth; i += 74) {
            float f = i;
            canvas.drawLine(f, 0.0f, f, measuredHeight, this.paint);
        }
        for (int i2 = 0; i2 < measuredHeight; i2 += 74) {
            float f2 = i2;
            canvas.drawLine(0.0f, f2, measuredWidth, f2, this.paint);
        }
    }
}
