package com.cloudyotech.mobile.interfaces

import android.view.View

/**
 * @description
 * @author JerryYin
 * @create 2018-05-17 16:40
 **/
interface onItemClickListener {

    fun onItemClick(view: View, position: Int)

    fun onItemLongClick(view: View, position: Int)

}