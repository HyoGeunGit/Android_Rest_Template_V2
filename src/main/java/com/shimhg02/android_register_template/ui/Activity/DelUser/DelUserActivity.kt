package com.shimhg02.android_register_template.ui.Activity.DelUser


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shimhg02.android_register_template.R
import com.shimhg02.android_register_template.network.Retrofit.Client
import kotlinx.android.synthetic.main.activity_del_user.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val PREFERENCE = "com.shimhg02.template"

/**
 * @description 유저 탈퇴 화면 {Activity}
 */


class DelUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_del_user)

        del_btn.setOnClickListener {
            delUser()
        }

    }

    private fun delUser(){
        val pref = getSharedPreferences(PREFERENCE, AppCompatActivity.MODE_PRIVATE)
        Client.retrofitService.delUser(pref.getString("uuid","").toString()).enqueue(object :
            Callback<Void> {
            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                when (response!!.code()) {
                    200 -> {
                        finish()
                        finish()
                        val homeIntent = Intent(Intent.ACTION_MAIN)
                        homeIntent.addCategory(Intent.CATEGORY_HOME)
                        homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(homeIntent)
                    }
                    500 -> toast("서버 에러")
                }
            }
            override fun onFailure(call: Call<Void>?, t: Throwable?) {
            }
        })
    }
}

