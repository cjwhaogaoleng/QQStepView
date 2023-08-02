package com.example.customview2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class QQStepViewK  constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {
    private var mOuterColor = Color.BLUE
    private var mInnerColor = Color.RED
    private var mBorderWidth = 20 //20px
    private var mStepTextSize = 20
    private var mStepTextColor = Color.GRAY
    private val mOutPaint: Paint
    private val mInPaint: Paint
    private val mTextPaint: Paint
    private var mStepMax = 0
    private var mCurrentStep = 0

    init {

        //1.分析效果
        //2.编写attrs
        //3.布局中使用
        //4.在自定义view中获取自定义属性
        val array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView)
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor, mInnerColor)
        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor, mOuterColor)
        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor)
        mBorderWidth =
            array.getDimension(R.styleable.QQStepView_stepBorderWidth, mBorderWidth.toFloat())
                .toInt()
        mStepTextSize =
            array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, mStepTextSize)

        //外部
        mOutPaint = Paint()
        mOutPaint.isAntiAlias = true
        mOutPaint.strokeWidth = mBorderWidth.toFloat()
        mOutPaint.color = mOuterColor
        mOutPaint.style = Paint.Style.STROKE
        mOutPaint.strokeCap = Paint.Cap.ROUND

        //内部
        mInPaint = Paint()
        mInPaint.isAntiAlias = true
        mInPaint.strokeWidth = mBorderWidth.toFloat()
        mInPaint.color = mInnerColor
        mInPaint.style = Paint.Style.STROKE
        mInPaint.strokeCap = Paint.Cap.ROUND

        //文字
        mTextPaint = Paint()
        mTextPaint.isAntiAlias = true
        //        mTextPaint.setStrokeWidth(mBorderWidth);
        mTextPaint.color = mStepTextColor
        mTextPaint.textSize = mStepTextSize.toFloat()
        //        mTextPaint.setStyle(Paint.Style.STROKE);
//        mTextPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    //5.onMeasure
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val side = Math.min(width, height)
        setMeasuredDimension(side, side)
    }

    //6.画圆弧 文字
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val error = mBorderWidth / 2

        //外弧
        val rectF = RectF(
            error.toFloat(),
            error.toFloat(),
            (width - error).toFloat(),
            (height - error).toFloat()
        )
        canvas.drawArc(rectF, 135f, 270f, false, mOutPaint)

        //内弧
        val sweepAngle = mCurrentStep.toFloat() / mStepMax
        canvas.drawArc(rectF, 135f, sweepAngle * 270, false, mInPaint)

        //文字
        val text = mCurrentStep.toString() + ""
        val textBound = Rect()
        mTextPaint.getTextBounds(text, 0, text.length, textBound)
        val x = (width - textBound.width()) / 2
        val fontMetrics = mTextPaint.fontMetrics
        val dy = ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom).toInt()
        val baseLine = height / 2 + dy
        canvas.drawText(text, x.toFloat(), baseLine.toFloat(), mTextPaint)
        //        canvas.drawText(text, x, getWidth()/2, mTextPaint);
    }

    //7。其他处理
//    @kotlin.jvm.Synchronized
    fun setMaxStep(maxStep: Int) {
        mStepMax = maxStep
    }

//    @kotlin.jvm.Synchronized
    fun setCurrentStep(currentStep: Int) {
        mCurrentStep = currentStep
        //不断绘制
        invalidate()

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