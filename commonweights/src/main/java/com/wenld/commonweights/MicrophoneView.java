package com.wenld.commonweights;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.mode;

/**
 * Created by wenld on 2017/1/12.
 */

public class MicrophoneView extends View {

    Context mContext;
    private final TypedArray typedArray;
    public final static int MODEL_LOADING = 3;//标识中
    public final static int MODEL_PLAY = 2; //播放
    public final static int MODEL_RECORD = 1;//录制
    /**
     * 默认是录制模式
     */
    private int model = MODEL_RECORD;

    //View默认最小宽度
    private static final int DEFAULT_MIN_WIDTH = 400;

    //圆环的边距
    private int pandding = 10;
    //圆环的宽度
    private float ringWidth = 3;
    //圆环的颜色
    private int ringColor;
    //loading 加载的颜色
    private int loadingColor;
    //声波环颜色
    private int voiceRingColor;
    //声波颜色
    private int voiceColor;

    /**
     * 圆环渐变颜色
     */
    private int[] doughnutColors = new int[]{0x3045C3E5, 0x1245C3E5, 0x0545C3E5, 0x1245C3E5, 0x3045C3E5, 0x3045C3E5, 0xC545C3E5, 0xDD45C3E5, 0xFF45C3E5, 0xDD45C3E5, 0xC545C3E5, 0x3045C3E5};
    /**
     * 声波线宽度
     */
    private float voiceWidth = 2;
    /**
     * 音量
     */
    private int volume = 1;
    /**
     * 音量等级
     */
    private int volumeLevel = 2;

    Bitmap bitmap;


    private String playHintText;    //文字
    private float textHintSize;
    private int textHintColor;

    private float progress = 0;

    private Paint mPaint;
    Paint paintText;

    private Timer progressTimer = new Timer(true);
    private TimerTask progressTask;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {

            } else if (msg.what == 2) {
                progress += 360.00 / (1 * 950.00 / 5.00);
//                Log.d(TAG,"progress:"+progress);
                if (progress > 360) {
                    progress = progress - 360;
                    postInvalidate();
                } else
                    postInvalidate();
            }
        }
    };

    public MicrophoneView(Context context) {
        this(context, null);
    }

    public MicrophoneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.recordView);
        initAtts();

        mPaint = new Paint();
        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private void initAtts() {
        model = typedArray.getInt(R.styleable.recordView_model, MODEL_RECORD);
        playHintText = typedArray.getString(R.styleable.recordView_playHintText);
        textHintSize = typedArray.getDimension(R.styleable.recordView_hintTextSize, dip2px(mContext, 15));
        textHintColor = typedArray.getColor(R.styleable.recordView_textHintColor, Color.parseColor("#45C3E5"));

        ringColor = typedArray.getColor(R.styleable.recordView_ringColor, Color.parseColor("#DAF6FE"));
        loadingColor = typedArray.getColor(R.styleable.recordView_loadingColor, Color.parseColor("#45C3E5"));

        voiceColor = typedArray.getColor(R.styleable.recordView_voiceColor, Color.parseColor("#2245C3E5"));
        voiceRingColor = typedArray.getColor(R.styleable.recordView_voiceRingColor, Color.parseColor("#45C3E5"));
        converColor();

        ringWidth = typedArray.getDimension(R.styleable.recordView_ringWidth, dip2px(mContext, 3));

        voiceWidth = typedArray.getDimension(R.styleable.recordView_voiceWidth, dip2px(mContext, 1));

        Drawable drawable = typedArray.getDrawable(R.styleable.recordView_microphoneBitmap);
        if (drawable != null) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_microphone);
        }
    }

    /**
     * 当布局为wrap_content时设置默认长宽
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
    }

    private int measure(int origin) {
        int result = DEFAULT_MIN_WIDTH;
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
        super.onDraw(canvas);
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getWidth() - getPaddingRight();
        int bottom = getHeight() - getPaddingBottom();

        switch (model) {
            case MODEL_LOADING: {
                drawRing(canvas);
                drawText(canvas);
                drawProgress(canvas);
            }
            break;
            case MODEL_RECORD:
            case MODEL_PLAY:
                onDrawMicrophone(canvas);
                drawArc(canvas);
                break;
        }
    }


    /**
     * 绘制 麦克风图标
     *
     * @param canvas
     */
    private void onDrawMicrophone(Canvas canvas) {

        float w = getWidth() / 2 - dip2px(mContext, pandding + 5);
        float h = getHeight() / 2 - dip2px(mContext, pandding + 5);
        RectF rectF = new RectF(getWidth() / 2 - w / 2
                , getHeight() / 2
                , w / 2 + getWidth() / 2
                , getHeight() / 2 + h);
        canvas.drawBitmap(bitmap, null, rectF, null);
    }

    private void drawText(Canvas canvas) {
        /**
         * 画中间文字
         * */

        paintText.setTextSize(textHintSize);
        paintText.setColor(textHintColor);
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        paintText.setTextAlign(Paint.Align.CENTER);
        if (playHintText == null) {
            playHintText = "正在播放录音.";
        }
        canvas.drawText(playHintText, getWidth() / 2, getHeight() / 2 - (fm.bottom - fm.top) / 2 - fm.top, paintText);
    }

    private void drawRing(Canvas canvas) {
        /**
         * 外层 圆环
         */
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(ringWidth);
        mPaint.setColor(ringColor);
        RectF oval = new RectF(dip2px(mContext, pandding)
                , dip2px(mContext, pandding)
                , getWidth() - dip2px(mContext, pandding)
                , getHeight() - dip2px(mContext, pandding));
        canvas.drawArc(oval, 0, 360, false, mPaint);    //绘制圆弧
    }

    /**
     * 绘制圆弧
     */
    private void drawProgress(Canvas canvas) {
        for (int i = 0; i < 3; i++) {
            drawProgress(i, canvas);
        }
        mPaint.reset();
    }

    /**
     * 绘制圆弧
     */
    private void drawProgress(int i, Canvas canvas) {
        mPaint.setColor(loadingColor);
        mPaint.setStrokeWidth(ringWidth);
//        if(i%2==0){
//            mPaint.setPathEffect(null);
//        }else{
//            PathEffect effects = new DashPathEffect(new float[]{100,60,50,20},1);
//            mPaint.setPathEffect(effects);
//        }
//        mPaint.setShader(new SweepGradient(getWidth() / 2, getHeight() / 2, doughnutColors, null));
        float length = (float) (45 * Math.sin(Math.toRadians(i * 60 + progress)) + 75);
        RectF oval = new RectF(dip2px(mContext, pandding + 2 * i)
                , dip2px(mContext, pandding + 2 * i)
                , getWidth() - dip2px(mContext, pandding + 2 * i)
                , getHeight() - dip2px(mContext, pandding + 2 * i));
        canvas.drawArc(oval, (120 - length) + progress + i * 80, length, false, mPaint);    //绘制圆弧
    }

    private void drawArc(Canvas canvas) {
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setColor(getResources().getColor(R.color.RoundFillColor));
        mPaint.setStrokeWidth(voiceWidth);
        mPaint.setShader(new SweepGradient(getWidth() / 2, getHeight() / 2, doughnutColors, null));
        float r = getWidth() / 2f - dip2px(mContext, pandding);
        for (int i = 5; i > 0; i--) {
            drawArc(r, i, canvas);
        }
        drawLightArc(r, volumeLevel, canvas);
        mPaint.reset();
    }

    private void drawArc(float r, int i, Canvas canvas) {
        float currentR = r / 5 * (i);
        RectF oval = new RectF(getWidth() / 2 - currentR
                , getHeight() / 2 - currentR
                , getWidth() / 2 + currentR
                , getHeight() / 2 + currentR);
        canvas.drawArc(oval, 160, 360, false, mPaint);    //绘制圆弧
    }

    /**
     * 亮
     *
     * @param r
     * @param i
     * @param canvas
     */
    private void drawLightArc(float r, int i, Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
        mPaint.setShader(new SweepGradient(getWidth() / 2, getHeight() / 2, voiceColor, voiceColor));
        float currentR = r / 20 * (i);

        RectF oval = new RectF(getWidth() / 2 - currentR
                , getHeight() / 2 - currentR
                , getWidth() / 2 + currentR
                , getHeight() / 2 + currentR);
        canvas.drawArc(oval, 160, 360, true, mPaint);    //绘制圆弧
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }

    public void setModel(int model) {
        this.model = model;
        if (model == MODEL_RECORD) {

        } else if (model == MODEL_PLAY) {

        } else if (model == MODEL_LOADING) {
            start();
        }
        postInvalidate();
    }

    public int getModel() {
        return model;
    }

    public void start() {
        stop();
        progressTimer.schedule(progressTask = new TimerTask() {
            public void run() {
                Message msg = new Message();
                msg.what = 2;
                mHandler.sendMessage(msg);
            }
        }, 0, 5);
    }

    public void stop() {
        try {
            if (progressTask != null)
                progressTask.cancel();
            postInvalidate();
        } catch (Exception e) {

        }
    }

    public void setVolume(int volume) {
        if (model == MODEL_RECORD || mode == MODEL_PLAY) {
            if (volume <= 0 && volume > 100) {
                throw new NullPointerException("数值在0-100, 请转换。");
            } else {
                this.volume = volume * 2;
                if (this.volume < 10) {
                    volumeLevel = 1;
                    return;
                } else if (this.volume == 200) {
                    volumeLevel = 20;
                    return;
                } else {
                    volumeLevel = this.volume / 10;
                }
            }
            postInvalidate();
        }
    }

    public void cancel() {
        try {
            progress = 0;
            postInvalidate();
            progressTask.cancel();
        } catch (Exception e) {

        }
    }

    private void setVoiceRingColor(int color) {
        voiceRingColor = color;
        converColor();
    }

    public void setRingWidth(float ringWidth) {
        this.ringWidth = ringWidth;
    }

    public void setRingColor(int ringColor) {
        this.ringColor = ringColor;
    }

    public void setVoiceColor(int voiceColor) {
        this.voiceColor = voiceColor;
    }

    public void setVoiceWidth(float voiceWidth) {
        this.voiceWidth = voiceWidth;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setPlayHintText(String playHintText) {
        this.playHintText = playHintText;
    }

    public void setTextHintSize(float textHintSize) {
        this.textHintSize = textHintSize;
    }

    public void setTextHintColor(int textHintColor) {
        this.textHintColor = textHintColor;
    }

    private void converColor() {
        String str = Integer.toHexString(voiceRingColor).substring(2, 8);
        doughnutColors = new int[]{Color.parseColor("#30" + str), Color.parseColor("#12" + str), Color.parseColor("#54" + str), Color.parseColor("#12" + str),
                Color.parseColor("#30" + str), Color.parseColor("#30" + str), Color.parseColor("#C5" + str), Color.parseColor("#DD" + str), Color.parseColor("#FF" + str),
                Color.parseColor("#DD" + str), Color.parseColor("#C5" + str), Color.parseColor("#30" + str)};
    }
}
