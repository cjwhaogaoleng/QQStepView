package com.example.customview2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class QQStepView extends View {

    private int mOuterColor = Color.BLUE;
    private int mInnerColor = Color.RED;

    private int mBorderWidth = 20;//20px

    private int mStepTextSize = 20;
    private int mStepTextColor = Color.GRAY;

    private Paint mOutPaint,mInPaint, mTextPaint;

    private int mStepMax;
    private int mCurrentStep;





    public QQStepView(Context context) {
        this(context, null);    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //1.分析效果
        //2.编写attrs
        //3.布局中使用
        //4.在自定义view中获取自定义属性

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor, mInnerColor);
        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor);

        mBorderWidth = (int) array.getDimension(R.styleable.QQStepView_stepBorderWidth, mBorderWidth);
        mStepTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize,mStepTextSize);

        //外部
        mOutPaint = new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setStrokeWidth(mBorderWidth);
        mOutPaint.setColor(mOuterColor);
        mOutPaint.setStyle(Paint.Style.STROKE);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);

        //内部
        mInPaint = new Paint();
        mInPaint.setAntiAlias(true);
        mInPaint.setStrokeWidth(mBorderWidth);
        mInPaint.setColor(mInnerColor);
        mInPaint.setStyle(Paint.Style.STROKE);
        mInPaint.setStrokeCap(Paint.Cap.ROUND);

        //文字
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
//        mTextPaint.setStrokeWidth(mBorderWidth);
        mTextPaint.setColor(mStepTextColor);
        mTextPaint.setTextSize(mStepTextSize);
//        mTextPaint.setStyle(Paint.Style.STROKE);
//        mTextPaint.setStrokeCap(Paint.Cap.ROUND);

    }
    //5.onMeasure

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int side = Math.min(width, height);
        setMeasuredDimension(side, side);
    }

    //6.画圆弧 文字

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int error = mBorderWidth / 2;

        //外弧
        RectF rectF = new RectF(error, error, getWidth()-error, getHeight()-error);
        canvas.drawArc(rectF, 135, 270, false, mOutPaint);

        //内弧

        float sweepAngle = (float) mCurrentStep/mStepMax;
        canvas.drawArc(rectF, 135, sweepAngle* 270, false, mInPaint);

        //文字
        String text = mCurrentStep + "";
        Rect textBound = new Rect();
        mTextPaint.getTextBounds(text,0,text.length(),textBound);
        int x = (getWidth() - textBound.width()) / 2;

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseLine, mTextPaint);
//        canvas.drawText(text, x, getWidth()/2, mTextPaint);
    }

    //7。其他处理
    public synchronized void setMaxStep(int maxStep) {
        this.mStepMax = maxStep;
    }

    public synchronized void setCurrentStep(int currentStep) {
        this.mCurrentStep = currentStep;
        //不断绘制
        invalidate();

        /*
        一直向上走 走到最外层
        draw -》 dispatchDraw 一路向下画 一直画到当前调用invalidate的view的onDraw方法
        invalidate牵连整个layout的布局的view

         */

        /*
        为何不能再子线程中更新UI
        开了线程，更新UI，一般会调用setText，setImageView
        都会掉到ViewRootImpl的 checkThread（）的方法 -》 用来检测线程
        如果主线程和当前线程不一样，就会报错

         */
    }
}
