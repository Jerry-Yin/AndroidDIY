package com.android.jay.androiddiy.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.jay.androiddiy.R
import com.android.jay.androiddiy.model.News
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE
import android.widget.ProgressBar


/**
 * @description
 * @author JerryYin
 * @create 2018-07-25 10:44
 **/
class CusRecyclerViewAdapter2(
        private var mC: Context,
        private var mDataList: ArrayList<News>
) : RecyclerView.Adapter<CusRecyclerViewAdapter2.CusViewHolder>() {

    companion object {
        val TAG = "CusRecyclerViewAdapter.CLASS"

        val ITEM_TYPE_NORMAL = 0 //item类型---普通item
        val ITEM_TYPE_LOAD = 1   //item类型---加载更多

    }

    private var mItemType = ITEM_TYPE_NORMAL    //默认普通类型item
    private var mShowLoadItem = true

    private var mViewHolder: CusViewHolder? = null

    private var mOnScrollToItemListener: OnScrollToItemListener? = null
    private var mOnScrollItemListener: OnScrollItemListener? = null


    class CusViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var img = itemView!!.findViewById<ImageView>(R.id.img_data)
        var tv = itemView!!.findViewById<TextView>(R.id.tv_data)
        var time = itemView!!.findViewById<TextView>(R.id.tv_time)


        //加载更多的item
        var tv_load = itemView!!.findViewById<TextView>(R.id.tv_load_more)
        var pb_load = itemView!!.findViewById<ProgressBar>(R.id.progressBar_load)

        fun setLoadText(text: CharSequence) {
            if (tv_load != null)
                tv_load.text = text
        }

        fun setLoadProgressBarVisibility(show: Boolean) {
            if (pb_load != null) {

                if (show)
                    pb_load.visibility = View.VISIBLE
                else
                    pb_load.visibility = View.GONE
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CusViewHolder {
        var view: View? = null
        if (mItemType == ITEM_TYPE_NORMAL) {
            view = LayoutInflater.from(mC).inflate(R.layout.item_data, parent, false)
        } else if (mItemType == ITEM_TYPE_LOAD) {
            view = LayoutInflater.from(mC).inflate(R.layout.item_load_more, parent, false)
        }
        mViewHolder = CusViewHolder(view)
        return mViewHolder!!
    }

    override fun getItemCount(): Int {
        var size = mDataList.size
        if (mShowLoadItem)
            size = mDataList.size + 1
        return size
    }

    override fun onBindViewHolder(holder: CusViewHolder, position: Int) {
        if (mShowLoadItem && position == itemCount - 1) {
            //最后一项  do nothing
        } else if (position < itemCount) {
            //正常情况
            holder.itemView.visibility = View.VISIBLE

            var news = mDataList[position]
            holder.img.setImageDrawable(mC.resources.getDrawable(news.imgId))
            holder.tv.text = news.content
            holder.time.text = news.time

            holder.itemView.setOnClickListener {
                object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        Toast.makeText(mC, "clicked news " + position, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        if (mShowLoadItem && position == itemCount - 1) {
            return ITEM_TYPE_LOAD
        }
        return ITEM_TYPE_NORMAL
//        return super.getItemViewType(position)
    }

    fun setLoadItemVisibility(show: Boolean) {
        if (mShowLoadItem != show) {
            mShowLoadItem = show
            notifyDataSetChanged()
        }
    }

    fun setLoadItemState(isLoading: Boolean) {
        if (isLoading) {
            mViewHolder!!.setLoadText("正在加载...")
            mViewHolder!!.setLoadProgressBarVisibility(true)
        } else {

            mViewHolder!!.setLoadText("上拉加载更多")
            mViewHolder!!.setLoadProgressBarVisibility(false)
        }

    }


    /**
     * 当有新的itemView加入到window中时，即会调用
     */
//    @SuppressLint("LongLogTag")
//    override fun onViewAttachedToWindow(holder: CusViewHolder) {
//        super.onViewAttachedToWindow(holder)
//        var position = holder.layoutPosition
//        var p1 = holder.adapterPosition
//        var p2 = holder.oldPosition
//        Log.d(TAG, "p = " + position)
//        Log.d(TAG, "p1 = " + p1)
//        Log.d(TAG, "p2 = " + p2)
//
//        if (position == mDataList.size - 1) {
//            Log.d(TAG, "position = mDataList.size-1 = " + position)
//            if (mOnScrollToItemListener != null) {
//                mOnScrollToItemListener!!.onScrollBottomItem(position, holder.itemView)
//            }
//            if (mOnScrollItemListener != null)
//                mOnScrollItemListener!!.isBottomItem = true
//
//        } else if (position == 0) {
//            if (mOnScrollToItemListener != null)
//                mOnScrollToItemListener!!.onScrollTopItem(holder.itemView)
//
//
//            if (mOnScrollItemListener != null)
//                mOnScrollItemListener!!.isTopItem = true
//        } else {
//
//            if (mOnScrollItemListener != null) {
//                mOnScrollItemListener!!.isBottomItem = false
//                mOnScrollItemListener!!.isTopItem = false
//
//            }
//        }
//    }


    fun setOnScrollToItemListener(listener: OnScrollToItemListener) {
        this.mOnScrollToItemListener = listener
    }

    fun setOnScrollItemListener(listener: OnScrollItemListener) {
        this.mOnScrollItemListener = listener
    }

    interface OnScrollToItemListener {

        fun onScrollTopItem(temView: View)

        fun onScrollBottomItem(position: Int, itemView: View)
    }


    open class OnScrollItemListener : RecyclerView.OnScrollListener() {
        var isTopItem = false
        var isBottomItem = false
    }


    /**
     * 上滑 加载更多操作辅助类
     */
    open class OnLoadMoreScrollListener(
            private val recyclerView: RecyclerView,
            private val mAdapter: CusRecyclerViewAdapter2
    ) : RecyclerView.OnScrollListener() {

        private var mLayoutManager: RecyclerView.LayoutManager? = null
        private var mListener: LoadMoreListener? = null
        /** 是否正在加载中  */
        private var mLoading = false
        /** 上拉刷新功能是否开启  */
        private var mIsSwipeToLoadEnabled = true

        init {
            mLayoutManager = recyclerView.layoutManager
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            if (mIsSwipeToLoadEnabled &&
                    newState == SCROLL_STATE_IDLE &&
                    !mLoading) {

                val linearLayoutManager = mLayoutManager as LinearLayoutManager
                val lastCompletePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()

                // only when the complete visible item is second last
                if (lastCompletePosition == linearLayoutManager.getItemCount() - 2) {
                    val firstCompletePosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
                    val child = linearLayoutManager.findViewByPosition(lastCompletePosition)
                            ?: return
                    val deltaY = recyclerView!!.bottom - recyclerView.paddingBottom - child.bottom
                    if (deltaY > 0 && firstCompletePosition != 0) {
                        recyclerView.smoothScrollBy(0, -deltaY)
                    }
                } else if (lastCompletePosition == linearLayoutManager.getItemCount() - 1) {
                    // 最后一项完全显示, 触发操作, 执行加载更多操作 禁用回弹判断
                    mLoading = true
                    mAdapter.setLoadItemState(true)
                    if (mListener != null) {
                        mListener!!.onLoad()
                    }
                }
            }
        }

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
        }

        /** 上拉刷新功能是否开启  */
        fun setSwipeToLoadEnabled(isSwipeToLoadEnabled: Boolean) {
            if (mIsSwipeToLoadEnabled != isSwipeToLoadEnabled) {
                mIsSwipeToLoadEnabled = isSwipeToLoadEnabled
                mAdapter.setLoadItemVisibility(isSwipeToLoadEnabled)
            }
        }

        /** 设置LoadMore Item为加载完成状态, 上拉加载更多完成时调用  */
        fun setLoadMoreFinish() {
            mLoading = false
            mAdapter.setLoadItemState(false)
        }

        /** 上拉操作触发时调用的接口  */
        fun setLoadMoreListener(loadMoreListener: LoadMoreListener) {
            mListener = loadMoreListener
        }

        interface LoadMoreListener {
            fun onLoad()
        }
    }

}