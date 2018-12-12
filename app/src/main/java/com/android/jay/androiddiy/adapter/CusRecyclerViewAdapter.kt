package com.android.jay.androiddiy.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.jay.androiddiy.R
import com.android.jay.androiddiy.model.News

/**
 * @description
 * @author JerryYin
 * @create 2018-07-25 10:44
 **/
open class CusRecyclerViewAdapter(
        public var mC: Context,
        public var mDataList: ArrayList<News>
) : RecyclerView.Adapter<CusRecyclerViewAdapter.CusViewHolder>() {


    open class CusViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var img = itemView!!.findViewById<ImageView>(R.id.img_data)
        var tv = itemView!!.findViewById<TextView>(R.id.tv_data)
        var time = itemView!!.findViewById<TextView>(R.id.tv_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CusViewHolder {
//        var view = View.inflate(mC, R.layout.item_data, parent)
        var view = LayoutInflater.from(mC).inflate(R.layout.item_data, parent, false)
        return CusViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onBindViewHolder(holder: CusViewHolder, position: Int) {
        var news = mDataList[position]
        if (holder != null) {
            holder.img.setImageDrawable(mC.resources.getDrawable(news.imgId))
            holder.tv.text = news.content
            holder.time.text = news.time
        }
        holder.itemView.setOnClickListener {
            object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    Toast.makeText(mC, "clicked news " + position, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}