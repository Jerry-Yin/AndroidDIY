package com.android.jay.androiddiy.activitys

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.jay.androiddiy.R
import com.android.jay.androiddiy.views.YinTabButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {


        btn1.setOnClickListener {
            startActivity(Intent(this, TabViewActivity::class.java))
        }

        btn_refresh.setOnClickListener {
            startActivity(Intent(this, RefreshViewActivity::class.java))
        }
    }
}
