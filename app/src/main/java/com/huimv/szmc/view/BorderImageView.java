package com.huimv.szmc.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public class BorderImageView extends ImageView {

    public BorderImageView(Context context) {
        super(context);
    }
    public BorderImageView(Context context, AttributeSet attrs,
            int defStyle) {
          super(context, attrs, defStyle);
    }

    public BorderImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        Rect rec = canvas.getClipBounds();
/*        rec.bottom--;
        rec.right--;*/
        Paint paint = new Paint();
      //设置边框颜色
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        //设置边框宽度
        paint.setStrokeWidth(5);
        canvas.drawRect(rec, paint);
    }
}
