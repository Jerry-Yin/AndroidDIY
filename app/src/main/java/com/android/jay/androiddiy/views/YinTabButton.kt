package com.android.jay.androiddiy.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.support.annotation.Dimension
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.android.jay.androiddiy.R

/**
 * @description custom tab button
 * @author JerryYin
 * @create 2018-06-11 11:13
 **/
class YinTabButton : LinearLayout {

    private val TAG = "YinTabButton.ktt"
    private var c: Context

//    private var textView: TextView? = null
//    private var line: View? = null

//    private var parentLayout: LinearLayout? = null
//    private var childLayout: RelativeLayout? = null


    //画笔
//    private var paint: Paint = Paint()
    //默认水平方向
//    private var orientation = LinearLayout.HORIZONTAL

    private var mChildViews = ArrayList<LinearLayout>()

    //默认长度=3
    private var size = 3
    private var titles: List<String>? = listOf("TabA", "TabB", "TabC")
    private var textSize: Float? = 20.0f

    private var color: Int? = Color.parseColor("#636161")
    private var colorSelected: Int? = Color.parseColor("#00b0ff")

    //默认标签line高度2dp
    private var indicatorsHeight: Float = 5F
    //默认宽度match_parent
    private var indicatorsWidth: Float = 100F
    private var indicatorsVisible = false


    private var mOnClickListener: OnClickListener? = null;

    constructor(c: Context) : super(c) {
        this.c = c
        initView(c, null)
    }

    constructor(c: Context, attrs: AttributeSet) : super(c, attrs) {
        this.c = c
        initView(c, attrs)
    }


    @SuppressLint("Recycle")
    private fun initView(c: Context, attrs: AttributeSet?) {
        Log.d(TAG, "initView")
        Log.d(TAG, "childCount：" + this.childCount.toString())
        if (this.childCount > 0) {
            this.removeAllViews()
            mChildViews.clear()
            Log.d(TAG, " removed & now childCount：" + this.childCount.toString())
        }

        val typeArray = c.obtainStyledAttributes(attrs, R.styleable.YinTabButton)
        if (typeArray != null) {
            color = typeArray.getColor(R.styleable.YinTabButton_color, color!!)
            colorSelected = typeArray.getColor(R.styleable.YinTabButton_colorSelected, colorSelected!!)
            indicatorsVisible = typeArray.getBoolean(R.styleable.YinTabButton_indicatorsVisible, indicatorsVisible)
            indicatorsHeight = typeArray.getDimension(R.styleable.YinTabButton_indicatorsHeight, indicatorsHeight)
            indicatorsWidth = typeArray.getDimension(R.styleable.YinTabButton_setIndicatorWidth, indicatorsWidth)
        }

        for (i in 0..size - 1) {
            //带指示器
            val childView = LinearLayout(c)
            childView.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f)
            childView.orientation = LinearLayout.VERTICAL
            childView.gravity = Gravity.CENTER_HORIZONTAL
//            childView.setBackgroundColor()

            val text = TextView(c)
            text.text = titles!![i]
            if (textSize != null)
                text.textSize = textSize!!
            text.setTextColor(color!!)
            text.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.9f)
            text.gravity = Gravity.CENTER
            text.visibility = View.VISIBLE

            val indicators = View(c)
//            if (indicatorsWidth == null) {
//                indicatorsWidth = LinearLayout.LayoutParams.MATCH_PARENT
//            }
            indicators.layoutParams = LinearLayout.LayoutParams(indicatorsWidth.toInt(), indicatorsHeight.toInt())
            indicators.setBackgroundColor(color!!)

            if (!indicatorsVisible) {
//                indicators.setBackgroundColor(Color.TRANSPARENT)
                indicators.visibility = View.INVISIBLE
            }

            childView.addView(text)
            childView.addView(indicators)
            mChildViews.add(childView)

            if (mOnClickListener != null) {
                setOnClickListener(mOnClickListener!!)
            }
        }

        refreshColor(0)

        for (v in mChildViews)
            this.addView(v)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d(TAG, "omMeasure")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        initView(c)
        Log.d(TAG, "omDraw")
    }


    private fun refreshColor(index: Int) {
        for (i in 0..mChildViews.size - 1) {
            if (i == index) {
                (mChildViews[i].getChildAt(0) as TextView).setTextColor(colorSelected!!)
                (mChildViews[i].getChildAt(1)).visibility = View.VISIBLE
                (mChildViews[i].getChildAt(1)).setBackgroundColor(colorSelected!!)
            } else {
                (mChildViews[i].getChildAt(0) as TextView).setTextColor(color!!)
                (mChildViews[i].getChildAt(1)).visibility = View.GONE
            }
        }
    }

    fun setOnClickListener(listener: OnClickListener) {
        this.mOnClickListener = listener
        for (i in 0..mChildViews.size - 1) {
            mChildViews[i].setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    if (p0 != null) {
                        listener.onClick(p0, i)
                        refreshColor(i)
                    }
                }
            })
        }
    }


    /***--- 属性设置 --******/

    fun indicatorsVisible(show: Boolean) {
        this.indicatorsVisible = show
//        this.invalidate()
        initView(c, null)
    }

    fun setIndicatorWidth(width: Float) {
        this.indicatorsWidth = width
        initView(c, null)
    }

//    fun setOrientation(o: Int) {
//        this.orientation = o
//    }

    fun setColor(color: Int) {
        this.color = color
//        this.invalidate()
        initView(c, null)
    }

    fun setSelectedColor(color: Int) {
        this.colorSelected = color
//        this.invalidate()
        initView(c, null)
    }

    fun setTitles(titles: List<String>) {
        this.titles = titles
        this.size = titles.size
        initView(c, null)
//        this.invalidate()
    }


    interface OnClickListener : View.OnClickListener {

        override fun onClick(p0: View?) {
        }

        fun onClick(view: View, position: Int)
    }
}