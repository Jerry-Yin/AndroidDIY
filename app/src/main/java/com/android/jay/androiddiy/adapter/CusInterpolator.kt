package com.android.jay.androiddiy.adapter

import android.view.animation.LinearInterpolator

/**
 * @description
 * 插值器
 * @author JerryYin
 * @create 2018-07-21 16:35
 **/
class CusInterpolator : LinearInterpolator() {

    private var factor: Float = 0.15f


    override fun getInterpolation(input: Float): Float {
//        return super.getInterpolation(input)
        var polation = Math.pow(2.0, (-10 * input).toDouble()) * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1
        return polation.toFloat()
    }
}