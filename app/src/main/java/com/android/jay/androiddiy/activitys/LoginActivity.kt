package com.android.jay.androiddiy.activitys

import android.animation.*
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationSet
import com.android.jay.androiddiy.R
import com.android.jay.androiddiy.adapter.CusInterpolator
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_input.*

class LoginActivity : AppCompatActivity() {

    val TAG = "LoginActivity.CLASS"

    private var mWidthBtn: Float? = null
    private var mHeightBtn: Float? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()
    }

    private fun initViews() {
        btn_login.setOnClickListener { login() }
    }


    private fun login() {
        startAnimator()

    }

    private fun startAnimator() {
        //按钮控件的尺寸
        mWidthBtn = btn_login.measuredWidth.toFloat()
        mHeightBtn = btn_login.measuredHeight.toFloat()

        //隐藏输入框
        input_layout_name.visibility = View.INVISIBLE
        input_layout_psw.visibility = View.INVISIBLE

        inputAnimator(input_layout, mWidthBtn!!, mHeightBtn!!)
    }


    /**
     * 输入筐 部分 控件的动画效果
     *      从左右两侧缩进
     * view--控件
     * w\h -- 宽\高
     */
    fun inputAnimator(v: View, width: Float, height: Float) {
        var set = AnimatorSet() //动画集合


        //动画1
        var animator = ValueAnimator.ofFloat(0f, width)
        animator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(p0: ValueAnimator?) {
                var value: Float = p0!!.getAnimatedValue() as Float
                var params = v.layoutParams as ViewGroup.MarginLayoutParams
                params.leftMargin = value.toInt()
                params.rightMargin = value.toInt()
                v.layoutParams = params
            }
        })


        //动画2
        var animator2 = ObjectAnimator.ofFloat(input_layout, "scaleX", 1f, 0.5f)
        set.setDuration(500)
        set.setInterpolator(AccelerateDecelerateInterpolator())
        set.playTogether(animator, animator2)
        set.start()

        set.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                layout_progress.visibility = View.VISIBLE
                progressAnimator(layout_progress)
                input_layout.visibility = View.INVISIBLE

                requestLogin()
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }

        })
    }

    private fun requestLogin() {
        Thread(
                Runnable {
                    kotlin.run {
                        var i = 0
                        while (i < 2) {
                            i++
                            Thread.sleep(1000)
                            Log.d(TAG, "cur thread = " + Thread.currentThread() + " i=" + i)
                        }
                        runOnUiThread {
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            this.finish()
                        }
                    }
                }).start()
    }

    //进度条动画
    fun progressAnimator(v: View) {
        var animator = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1f);
        var animator2 = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1f);
        var animator3 = ObjectAnimator.ofPropertyValuesHolder(v, animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(CusInterpolator());
        animator3.start();
    }

}
