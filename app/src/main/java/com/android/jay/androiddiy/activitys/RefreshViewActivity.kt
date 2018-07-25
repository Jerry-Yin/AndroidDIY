package com.android.jay.androiddiy.activitys

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.View
import com.android.jay.androiddiy.R
import kotlinx.android.synthetic.main.activity_refresh_view.*

class RefreshViewActivity : AppCompatActivity() {

    companion object {
        val TAG = "RefreshViewActivity.CLASS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refresh_view)

        initViews()
        initData()
    }

    private fun initViews() {
//        swipeRefreshView.isRefreshing = true      //直接开启加载动画

        swipeRefreshView.setOnRefreshListener(mOnRefreshListener)
        swipeRefreshView.setColorSchemeColors(resources.getColor(R.color.colorAccent))
//        swipeRefreshView.setProgressBackgroundColorSchemeColor(resources.getColor(R.color.white_al))

        //是否允许子控件滑动
//        swipeRefreshView.canChildScrollUp()
//        swipeRefreshView.setOnChildScrollUpCallback(mOnChilScrollUpCallback)
    }

    private fun initData() {

    }


    //刷新完成回调接口
    private var mOnRefreshListener = object : SwipeRefreshLayout.OnRefreshListener {
        @SuppressLint("LongLogTag")
        override fun onRefresh() {
            Log.d(TAG, "onRefresh...")
            Thread(Runnable {
                kotlin.run {
                    var i = 1
                    while (i <= 2) {
                        i++
                        Thread.sleep(1000)
                        Log.d(TAG, "s = " + i)
                    }
                    runOnUiThread {
                        Log.d(TAG, "run main...")

                        //                        swipeRefreshView.clearAnimation()
                        //停止加载，隐藏加载进度条
                        swipeRefreshView.isRefreshing = false
                    }
                }
            }).start()
        }


//        Handler().postDelayed(Runnable {
//              //do something here
//        }, 2000)

    }


    private var mOnChilScrollUpCallback = object : SwipeRefreshLayout.OnChildScrollUpCallback {
        @SuppressLint("LongLogTag")
        override fun canChildScrollUp(parent: SwipeRefreshLayout, child: View?): Boolean {
            Log.d(TAG, "OnChildScrollUpCallback...")
            Log.d(TAG, "parent = " + parent)
            Log.d(TAG, "child = " + child)
            return true
        }

    }
}
