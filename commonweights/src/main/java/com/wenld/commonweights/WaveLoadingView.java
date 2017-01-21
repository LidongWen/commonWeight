package com.wenld.commonweights;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import static android.graphics.Color.parseColor;

/**
 * Created by wenld on 2017/1/19.
 */

public class WaveLoadingView extends View {
    //绘制波纹
    private Paint mWavePaint;
    private Path mWavePath;
    private PointF start, end, control1, control2; //水波纹  贝塞尔曲线 起点、终点 两个辅助点
    private PointF bottomLeft, bottomRight;

    private int waveColor;


    private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);//设置mode 为XOR
    //绘制圆
    private Paint mCirclePaint;
    private Bitmap mBitmap;
    private float circleR; //圆半径

    private int circleColor;

    //我们自己的画布
    private Canvas mCanvas;
    private int mWidth;
    private int mHeight;
    //View默认最小宽度
    private static final int DEFAULT_MIN_WIDTH = 200;

    //文字
    private Paint mTextPaint;
    private int textColor;
    private float textSize;
    private int unitTextColor;
    private float unitTextSize;
    private String unitText;

    //百分比
    private int mPercent = 0;


    float x;
    float y;
    boolean isLeft;

    private Timer progressTimer = new Timer(true);
    private TimerTask progressTask;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            if (msg.what == 1) {
//
//            } else if (msg.what == 2) {
//                progress += 360.00 / (1 * 950.00 / 5.00);
////                Log.d(TAG,"progress:"+progress);
//                if (progress > 360) {
//                    progress = progress - 360;
//                    postInvalidate();
//                } else
//                    postInvalidate();
//            }
        }
    };

    public WaveLoadingView(Context context) {
        super(context, null);
    }

    public WaveLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        initAttrs(attrs);

        mWavePaint = new Paint();
        mWavePath = new Path();

        mCirclePaint = new Paint();


        mTextPaint = new Paint();

        if (start == null) {
            start = new PointF();
            control1 = new PointF();
            control2 = new PointF();
            end = new PointF();

            bottomLeft = new PointF();
            bottomRight = new PointF();
        }
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.waveLoadingView);
            circleColor = ta.getColor(R.styleable.waveLoadingView_circle_Color, parseColor("#B3E5FC"));

            waveColor = ta.getColor(R.styleable.waveLoadingView_wave_Color, Color.parseColor("#03A9F4"));

            textColor = ta.getColor(R.styleable.waveLoadingView_text_Color, Color.parseColor("#FFFFFF"));
            textSize = ta.getDimension(R.styleable.waveLoadingView_text_Size, 80);

            unitTextSize = ta.getDimension(R.styleable.waveLoadingView_unitText_Size, 40);
            unitTextColor = ta.getColor(R.styleable.waveLoadingView_unitText_Color, Color.parseColor("#FFFFFF"));
            unitText = ta.getString(R.styleable.waveLoadingView_unitText);
            unitText = unitText == null ? "%" : unitText;
//            textSize = (int) ta.getDimension(R.styleable.FlikerProgressBar_FlikerProgressBar_textSize, dp2px(12));
//            loadingColor = ta.getColor(R.styleable.FlikerProgressBar_loadingColor, Color.parseColor("#1EC2B6"));
//            stopColor = ta.getColor(R.styleable.FlikerProgressBar_stopColor, Color.parseColor("#FFAF31"));
            ta.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mWidth = measure(widthMeasureSpec);
        mHeight = measure(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);

        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888); //生成一个bitmap
        mCanvas = new Canvas(mBitmap);//讲bitmp放在我们自己的画布上，实际上mCanvas.draw的时候 改变的是这个bitmap对象

        int mWidthPadding = mWidth - getPaddingLeft() - getPaddingRight();
        int mHeightPadding = mHeight - getPaddingTop() - getPaddingBottom();
        circleR = Math.min(mWidthPadding, mHeightPadding) / 2;


        bottomLeft.set(getPaddingLeft(), 2 * circleR + getPaddingTop());
        bottomRight.set(getPaddingLeft() + 2 * circleR, 2 * circleR + getPaddingTop());


    }


    private int measure(int origin) {
        int result = dip2px(DEFAULT_MIN_WIDTH);
        int specMode = MeasureSpec.getMode(origin);
        int specSize = MeasureSpec.getSize(origin);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {

        //清除掉图像 不然path会重叠
        mBitmap.eraseColor(parseColor("#00000000"));

        drawCircle();
        drawWave(canvas);
        drawText(canvas);

//        Paint a = new Paint();
//        a.setStyle(Paint.Style.STROKE);
//        Path path = new Path();
//        path.lineTo(0, circleR);
//        path.lineTo(mWidth, circleR);
//
//        canvas.drawPath(path, a);
//
//        path.reset();
//        path.lineTo(getPaddingLeft() + circleR, 0);
//        path.lineTo(getPaddingLeft() + circleR, mHeight);
//        canvas.drawPath(path, a);

        postInvalidateDelayed(10);

    }

    private void drawCircle() {
        mCirclePaint.setColor(circleColor);
        mCirclePaint.setAntiAlias(true);
        mCanvas.drawCircle(circleR + getPaddingLeft(), circleR + getPaddingTop(), circleR, mCirclePaint);
    }

    String str;
    float txtLength;

    private void drawText(Canvas canvas) {
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        str = mPercent + "";
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(textColor);
        txtLength = mTextPaint.measureText(str);
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        canvas.drawText(str, circleR + getPaddingLeft(), circleR + getPaddingTop() - (fm.bottom - fm.top) / 2 - fm.top, mTextPaint);

        mTextPaint.setTextSize(unitTextSize);
        canvas.drawText(unitText, circleR + getPaddingLeft() + txtLength / 2 + 20, getPaddingTop() + circleR - (fm.descent - fm.ascent) / 2 - fm.top, mTextPaint);
    }

    private void drawWave(Canvas canvas) {
        mWavePaint.setColor(waveColor);
        mWavePaint.setAntiAlias(true);
        mWavePaint.setStyle(Paint.Style.FILL);
        mWavePaint.setXfermode(mMode);

        if (x > 2 * circleR) {
            isLeft = true;
        } else if (x < 0) {
            isLeft = false;
        }

        if (isLeft) {
            x -= 3;
        } else {
            x += 3;
        }
        //水波纹
        y = 2 * circleR * (1 - mPercent / 100f) + getPaddingTop();
        start.set(getPaddingLeft(), y);
        control1.set(getPaddingLeft() + x, y + circleR / 3);
        control2.set(circleR + getPaddingLeft() + x, y - circleR / 3);
        end.set(getPaddingLeft() + 2 * circleR, y);
        //绘制贝塞尔曲线
        mWavePath.reset();
        mWavePath.moveTo(start.x, start.y);
        mWavePath.cubicTo(control1.x, control1.y, control2.x, control2.y, end.x, end.y);
        mWavePath.lineTo(bottomRight.x, bottomRight.y);
        mWavePath.lineTo(bottomLeft.x, bottomLeft.y);

        mCanvas.drawPath(mWavePath, mWavePaint);
        mWavePaint.setXfermode(null);

//        mCanvas.drawRect(mWidthPadding / 2 + getPaddingLeft(), mHeightPadding / 2 + getPaddingTop(), mWidthPadding / 2 + getPaddingLeft() + circleR, mHeightPadding / 2 + getPaddingTop() + circleR, mWavePaint);
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("WaveLoadingView", "::   " + w + "  " + h + "  " + oldw + "  " + oldh + "  ");

    }

    public void setPercent(int percent) {
        mPercent = percent;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setUnitTextColor(int unitTextColor) {
        this.unitTextColor = unitTextColor;
    }

    public void setUnitTextSize(float unitTextSize) {
        this.unitTextSize = unitTextSize;
    }

    public void setUnitText(String unitText) {
        this.unitText = unitText;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }

    public void setWaveColor(int waveColor) {
        this.waveColor = waveColor;
    }

    public void start() {
//        stop();
//        progressTimer.schedule(progressTask = new TimerTask() {
//            public void run() {
//                Message msg = new Message();
//                msg.what = 2;
//                mHandler.sendMessage(msg);
//            }
//        }, 0, 10);
    }

    public void stop() {
//        try {
//            if (progressTask != null)
//                progressTask.cancel();
//            postInvalidate();
//        } catch (Exception e) {
//
//        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    /**
     * dip转化像素
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


}
