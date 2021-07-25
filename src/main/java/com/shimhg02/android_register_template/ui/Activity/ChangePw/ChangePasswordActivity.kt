package com.shimhg02.android_register_template.ui.Activity.ChangePw

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.shimhg02.android_register_template.R
import com.shimhg02.android_register_template.network.Retrofit.Client
import kotlinx.android.synthetic.main.actitivity_change_password.*
import kotlinx.android.synthetic.main.actitivity_change_password.chk_tv
import kotlinx.android.synthetic.main.actitivity_change_password.pw_chk_tv
import kotlinx.android.synthetic.main.actitivity_change_password.pw_tv
import kotlinx.android.synthetic.main.activity_signup_idpw.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val PREFERENCE = "com.shimhg02.template"

/**
 * @description 비밀번호 변경 화면 {Activity}
 */


class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actitivity_change_password)
        fix_btn.isClickable = false
        fix_btn.setBackgroundResource(R.drawable.btn_signup_not_enable)
        checkPassWord()
        back.setOnClickListener {
            finish()
        }
    }

    private fun ChangePassWord(){
        val pref = getSharedPreferences(PREFERENCE, AppCompatActivity.MODE_PRIVATE)
        Client.retrofitService.fixPassword(pref.getString("uuid","").toString(), npw_tv.text.toString(), pw_tv.text.toString()).enqueue(object :
            Callback<Void> {
            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                when (response!!.code()) {
                    200 -> {
                        finish()
                    }
                    401 -> toast("현재 비밀번호가 올바르지 않습니다.")
                    500 -> toast("서버 에러")
                }
            }
            override fun onFailure(call: Call<Void>?, t: Throwable?) {
            }
        })
    }

    fun checkPassWord(){
        val pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
        val edit = findViewById(R.id.pw_chk_tv) as EditText
        edit.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (pw_tv.text.toString() != pw_chk_tv.text.toString()) {
                    chk_tv.text = "비밀번호가 일치하지 않습니다."
                    chk_tv.setTextColor(Color.RED)
                    fix_btn.isClickable = false
                    fix_btn.setBackgroundResource(R.drawable.btn_signup_not_enable)
                } else {
                    chk_tv.text = "일치합니다"
                    chk_tv.setTextColor(Color.GREEN)
                    fix_btn.isClickable = true
                    fix_btn.setBackgroundResource(R.drawable.btn_signup)
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                fix_btn.setOnClickListener {
                    if(chk_tv.text?.toString() == "" || pw_tv?.text.toString() == "" || id_tv?.text.toString() == ""){
                        toast("입력란을 전부 입력해주세요.")
                    }
                    else{
                        fix_btn.setOnClickListener {
                            ChangePassWord()
                        }
                    }
                }
            }

        })
    }
}