package com.android.jay.androiddiy.activitys

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.jay.androiddiy.R
import com.android.jay.androiddiy.views.YinTabButton
import kotlinx.android.synthetic.main.activity_tab_view.*

class TabViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_view)

        initView()
    }

    private fun initView() {

        tab.setOnClickListener(object : YinTabButton.OnClickListener {
            override fun onClick(view: View, position: Int) {
                Toast.makeText(this@TabViewActivity, "clicked tab " + position, Toast.LENGTH_SHORT).show()
            }

        })

        tab2.setOnClickListener(object : YinTabButton.OnClickListener {
            override fun onClick(view: View, position: Int) {
                Toast.makeText(this@TabViewActivity, "clicked tab " + position, Toast.LENGTH_SHORT).show()
            }

        })

        tab2.setOnScrollerListener(object : YinTabButton.OnScrollerListener{
            override fun onScroller(view: View, position: Int) {
                Toast.makeText(this@TabViewActivity, "scrolle to tab " + position, Toast.LENGTH_SHORT).show()

            }

        })

        tab3.setOnClickListener(object : YinTabButton.OnClickListener {
            override fun onClick(view: View, position: Int) {
                Toast.makeText(this@TabViewActivity, "clicked tab " + position, Toast.LENGTH_SHORT).show()
            }

        })

        tab3.setOnScrollerListener(object : YinTabButton.OnScrollerListener{
            override fun onScroller(view: View, position: Int) {
                Toast.makeText(this@TabViewActivity, "scrolle to tab " + position, Toast.LENGTH_SHORT).show()

            }

        })
    }
}
