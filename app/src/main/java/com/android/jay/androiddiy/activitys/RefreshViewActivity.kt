package com.android.jay.androiddiy.activitys

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.annotation.LongDef
import android.support.annotation.RequiresApi
import android.support.v4.widget.NestedScrollView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.jay.androiddiy.R
import com.android.jay.androiddiy.R.id.*
import com.android.jay.androiddiy.adapter.AdapterWrapper
import com.android.jay.androiddiy.adapter.CusRecyclerViewAdapter
import com.android.jay.androiddiy.adapter.SwipeToLoadHelper
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
//    private var mSwipeToLoadHelper: SwipeToLoadHelper
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

    }

    private fun initData() {
        mNewaAdapter = CusRecyclerViewAdapter(this, mNewsList)
        recyclerView1.layoutManager = LinearLayoutManager(this)
        recyclerView1.setHasFixedSize(true)
        recyclerView1.adapter = mNewaAdapter

//        mNewaAdapter!!.setOnScrollToItemListener(mOnScrollToItemListener)
//        mNewaAdapter!!.setOnScrollItemListener(mOnScrollerListener)
//        recyclerView1.addOnScrollListener(mOnScrollerListener)

        var adapterWapper = AdapterWrapper(mNewaAdapter)
        var swipeToLoadHelper = SwipeToLoadHelper(recyclerView1, adapterWapper)
        swipeToLoadHelper.setSwipeToLoadEnabled(true)
        swipeToLoadHelper.setLoadMoreListener(object : SwipeToLoadHelper.LoadMoreListener {
            @SuppressLint("LongLogTag")
            override fun onLoad() {
                Log.d(TAG, "onLoad()..")

            }

        })


        initLoadNews()
    }

    private fun initLoadNews() {
        Handler().postDelayed(Runnable {
            //do something here
            for (i in 0..9) {
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


//    private var mOnChilScrollUpCallback = object : SwipeRefreshLayout.OnChildScrollUpCallback {
//        @SuppressLint("LongLogTag")
//        override fun canChildScrollUp(parent: SwipeRefreshLayout, child: View?): Boolean {
//            Log.d(TAG, "OnChildScrollUpCallback...")
//            Log.d(TAG, "parent = " + parent)
//            Log.d(TAG, "child = " + child)
//            return true
//        }
//
//    }

//    private var mOnScrollerListener = object : CusRecyclerViewAdapter.OnScrollItemListener() {
//        @SuppressLint("LongLogTag")
//        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
//            super.onScrollStateChanged(recyclerView, newState)
//            Log.d(TAG, "onScrollStateChanged: newState=" + newState)
////            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//            /**new State 一共有三种状态
//             * SCROLL_STATE_IDLE：    静止中    0
//             * SCROLL_STATE_DRAGGING：拖拽中    1
//             * SCROLL_STATE_SETTLING：滑动到最后一个位置（第一个或者最后一个）2
//             **/
//
//            //这里进行加载更多数据的操作
//
//
////            } else
//            if (newState == RecyclerView.SCROLL_STATE_DRAGGING && isBottomItem) {
////                Log.d(TAG, "滑动到了最后一个！" + newState)
////                Toast.makeText(this@RefreshViewActivity, "已经到最后一条新数据，加载更多？", Toast.LENGTH_SHORT).show()
//                if (recyclerView1.computeVerticalScrollOffset() > 0) {
//                    // 有滚动距离，说明可以加载更多，解决了 items 不能充满 RecyclerView 的问题及滑动方向问题
//                    var isBottom = !recyclerView1.canScrollVertically(1)        //不能再向上滑动->最后一个
//                    if (isBottom) {
//                        view_load_more.visibility = View.VISIBLE
//                    }
//                }
////                else{
////                    view_load_more.visibility = View.GONE
////                }
////                isBottomItem = false
//            }
////            else if (!isBottomItem)
////                view_load_more.visibility = View.GONE
////            else {
////                view_load_more.visibility = View.GONE
////                isBottomItem = false
////            }
//        }
//
//        @SuppressLint("LongLogTag")
//        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
//            super.onScrolled(recyclerView, dx, dy)
//            Log.d(TAG, "onScrolled: " + "dx=" + dx + " dy=" + dy)
//
//        }
//    }
//
//    private var mOnScrollToItemListener = object : CusRecyclerViewAdapter.OnScrollToItemListener {
//        override fun onScrollTopItem(temView: View) {
////            Toast.makeText(this@RefreshViewActivity, "已经到第一条数据！" , Toast.LENGTH_SHORT).show()
//        }
//
//        override fun onScrollBottomItem(position: Int, itemView: View) {
//            Toast.makeText(this@RefreshViewActivity, "已经到最后一条新数据，加载更多？", Toast.LENGTH_SHORT).show()
//
//        }
//    }





}
