package zlei.bysj.cslk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by zl on 2017/6/5.
 */
public class SettingItemView extends View {

    private int width;
    private int height;
    private int bg_color;
    private String mainText;
    private Paint textPaint;
    private int mainTextSize;
    private int otherTextSize;
    private Paint linePaint;
    private String otherText;
    private Paint otherTextPaint;
    private Bitmap turnImage;
    private Paint drawablePaint;
    private Bitmap bitmap;
    private boolean isShowIcon;
    private boolean isShowLine;


    public SettingItemView(Context context) {
        this(context,null);
    }

    public SettingItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        int defColor = Color.parseColor("#ff0000");
        int defMainTextSize = 50;
        int defOtherTextSize = 30;
        TintTypedArray typedArray = TintTypedArray.obtainStyledAttributes(getContext(), attrs, R.styleable.SettingItemView);
        mainText = typedArray.getString(R.styleable.SettingItemView_mainText);
        otherText = typedArray.getString(R.styleable.SettingItemView_otherText);
        otherTextSize = typedArray.getInt(R.styleable.SettingItemView_otherTextSize,defOtherTextSize);
        int turnImageID = typedArray.getResourceId(R.styleable.SettingItemView_turnImage, R.mipmap.ic_launcher);
        bitmap = BitmapFactory.decodeResource(getResources(), turnImageID);
        bg_color = typedArray.getColor(R.styleable.SettingItemView_bg_color, defColor);
        mainTextSize = typedArray.getInt(R.styleable.SettingItemView_mainTextSize, defMainTextSize);
        isShowIcon = typedArray.getBoolean(R.styleable.SettingItemView_showIcon, true);
        isShowLine = typedArray.getBoolean(R.styleable.SettingItemView_showLine, true);
        textPaint = new Paint();
        textPaint.setTextSize(mainTextSize);
        textPaint.setColor(Color.BLACK);
        linePaint = new Paint();
        linePaint.setStrokeWidth(7);
        linePaint.setColor(Color.LTGRAY);
        otherTextPaint = new Paint();
        otherTextPaint.setTextSize(otherTextSize);
        otherTextPaint.setColor(Color.GRAY);
        drawablePaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        switch (widthMode) {

            case MeasureSpec.AT_MOST://wrap_content;
                // TODO: 2017/6/5
                break;
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        switch (heightMode) {

            case MeasureSpec.AT_MOST://wrap_content;
                // TODO: 2017/6/5
                break;
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        setMeasuredDimension(width,height);

        int turnImageWidth = bitmap.getWidth();
        int turnImageHeight = bitmap.getHeight();
        float scaleHeight = (float)height/3/ (float)turnImageHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleHeight, scaleHeight);
        try {
            turnImage = Bitmap.createBitmap(bitmap, 0, 0, turnImageWidth, turnImageHeight, matrix,true);
        }catch (Exception e){
            Log.e("sout",scaleHeight+ "onMeasure: "+e.getMessage() );
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mainText == null){
            mainText = "test";
        }
        if (otherText == null){
            otherText = "test";
        }
        //canvas.drawColor(bg_color);
        canvas.drawText(mainText,10,height/3*2,textPaint);
        if (isShowLine){
            canvas.drawLine(0,height+1,width,height+1,linePaint);
        }
        canvas.drawText(otherText,width/2-otherTextPaint.measureText(otherText)/2,height/3*2,otherTextPaint);
        if (isShowIcon){
            canvas.drawBitmap(turnImage,width-turnImage.getWidth()-10,height/3,drawablePaint);
        }
    }

    public String getOtherText() {
        return otherText;
    }

    public void setOtherText(String otherText) {
        this.otherText = otherText;
        postInvalidate();
    }
}
