package android.support.p003v7.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/* renamed from: android.support.v7.widget.CardViewJellybeanMr1 */
class CardViewJellybeanMr1 extends CardViewEclairMr1 {
    CardViewJellybeanMr1() {
    }

    public void initStatic() {
        RoundRectDrawableWithShadow.sRoundRectHelper = new RoundRectHelper() {
            public void drawRoundRect(Canvas canvas, RectF bounds, float cornerRadius, Paint paint) {
                canvas.drawRoundRect(bounds, cornerRadius, cornerRadius, paint);
            }
        };
    }
}
