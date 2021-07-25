package com.shimhg02.android_register_template.ui.Activity.ChangeName

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shimhg02.android_register_template.R
import com.shimhg02.android_register_template.network.Retrofit.Client
import kotlinx.android.synthetic.main.activity_change_name.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val PREFERENCE = "com.shimhg02.template"

/**
 * @description 이름 변경 화면 {Activity}
 */


class ChangeNameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_name)
        fix_btn.setOnClickListener {
            changeName()
        }
    }

    private fun changeName(){
        val pref = getSharedPreferences(PREFERENCE, AppCompatActivity.MODE_PRIVATE)
        Client.retrofitService.fixName(pref.getString("uuid","").toString(), name_tv.text.toString()).enqueue(object :
            Callback<Void> {
            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                when (response!!.code()) {
                    200 -> {
                        toast("성공적으로 이름을 변경했습니다!")
                        finish()
                    }
                    500 -> toast("서버 에러")
                }
            }
            override fun onFailure(call: Call<Void>?, t: Throwable?) {
            }
        })
    }
}