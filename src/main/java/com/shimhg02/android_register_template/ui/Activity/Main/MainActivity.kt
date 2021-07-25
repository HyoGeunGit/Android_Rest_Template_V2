package com.shimhg02.android_register_template.ui.Activity.Main


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.shimhg02.android_register_template.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @description 메인 화면 {Activity}
 */


class MainActivity : AppCompatActivity() {

    private val FeedFragment by lazy { com.shimhg02.android_register_template.ui.Fragment.Feed.FeedFragment() }
    private val SettingFragment by lazy { com.shimhg02.android_register_template.ui.Fragment.Setting.SettingFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavigationBar()
    }

    private fun initNavigationBar() {
        bnv_main.run {
            setOnNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.first -> {
                        changeFragment(FeedFragment)
                    }
                    R.id.second -> {
                        changeFragment(SettingFragment)
                    }
                }
                true
            }
            selectedItemId = R.id.first
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, fragment).commit()
    }
}
