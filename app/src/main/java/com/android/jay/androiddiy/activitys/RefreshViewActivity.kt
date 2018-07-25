package com.android.jay.androiddiy.activitys

import android.annotation.SuppressLint
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v4.widget.NestedScrollView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.jay.androiddiy.R
import com.android.jay.androiddiy.adapter.CusRecyclerViewAdapter
import com.android.jay.androiddiy.model.News
import kotlinx.android.synthetic.main.activity_refresh_view.*
import java.text.SimpleDateFormat

class RefreshViewActivity : AppCompatActivity() {

    companion object {
        val TAG = "RefreshViewActivity.CLASS"
    }

    private var mImages = intArrayOf(
            R.drawable.img_couple,
            R.drawable.img_jump,
            R.drawable.img_snow,
            R.drawable.img_travel)
    private var mContents = listOf(
            "This is a new data 0 of RecyclerView, just for test, you'll see!",
            "This is a new data 1 of RecyclerView, just for test, you'll see!",
            "This is a new data 2 of RecyclerView, just for test, you'll see!",
            "This is a new data 3 of RecyclerView, just for test, you'll see!")


    private var mNewaAdapter: CusRecyclerViewAdapter? = null
    private var mNewsList = ArrayList<News>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refresh_view)

        initViews()
        initData()
    }

    private fun initViews() {
        swipeRefreshView.isRefreshing = true      //直接开启加载动画

        swipeRefreshView.setOnRefreshListener(mOnRefreshListener)

        //设置进度条颜色
        //1.指定颜色
        swipeRefreshView.setColorSchemeColors(resources.getColor(R.color.colorAccent))
        //2.多个颜色：转一圈换一个颜色
//        swipeRefreshView.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark, R.color.white)

        //背景颜色  默认白色
//        swipeRefreshView.setProgressBackgroundColorSchemeColor(resources.getColor(R.color.white_al))

        //是否允许子控件滑动
//        swipeRefreshView.canChildScrollUp()
//        swipeRefreshView.setOnChildScrollUpCallback(mOnChilScrollUpCallback)


//        recyclerView1.setOnScrollChangeListener(mOnScrollChangeListener)

    }

    private fun initData() {
        mNewaAdapter = CusRecyclerViewAdapter(this, mNewsList)
        recyclerView1.layoutManager = LinearLayoutManager(this)
        recyclerView1.setHasFixedSize(true)
        recyclerView1.adapter = mNewaAdapter

        initLoadNews()
    }

    private fun initLoadNews() {
        Handler().postDelayed(Runnable {
            //do something here
            for (i in 0..4) {
                var p = (Math.random() * 3).toInt()
                var formate = SimpleDateFormat("YYYY-MM-dd hh:mm:ss")
                mNewsList.add(News(mImages[p], mContents[p], formate.format(System.currentTimeMillis())))
            }
            mNewaAdapter!!.notifyDataSetChanged()
            swipeRefreshView.isRefreshing = false
        }, 150)
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

                        loadMoreNews()
                    }
                }
            }).start()
        }

    }

    private fun loadMoreNews() {
        var sum = 0
        for (i in 0..1) {
            var p = (Math.random() * 3).toInt()
            var formate = SimpleDateFormat("YYYY-MM-dd hh:mm:ss")
            mNewsList.add(0, News(mImages[p], mContents[p], formate.format(System.currentTimeMillis())))
            sum++
        }

        mNewaAdapter!!.notifyDataSetChanged()
        //停止加载，隐藏加载进度条
        swipeRefreshView.isRefreshing = false
        if (sum > 0)
            Toast.makeText(this, "已成功加载" + sum + "条新数据！", Toast.LENGTH_SHORT).show()
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


//    private var mOnScrollChangeListener = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//        object : View.OnScrollChangeListener {
//            override fun onScrollChange(p0: View?, p1: Int, p2: Int, p3: Int, p4: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//        }
//    } else {
//        TODO("VERSION.SDK_INT < M")
//    }
}
