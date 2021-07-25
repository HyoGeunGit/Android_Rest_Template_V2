package com.shimhg02.android_register_template.ui.Activity.SignUp


import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shimhg02.android_register_template.R
import com.shimhg02.android_register_template.network.Data.Users.Users
import com.shimhg02.android_register_template.network.Retrofit.Client
import gun0912.tedkeyboardobserver.BaseKeyboardObserver
import gun0912.tedkeyboardobserver.TedKeyboardObserver
import kotlinx.android.synthetic.main.activity_signup_finish.*
import kotlinx.android.synthetic.main.activity_signup_info.*
import kotlinx.android.synthetic.main.activity_signup_info.next_btn
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

val PREFERENCE = "com.shimhg02.template"

/**
 * @description 회원가입 상세정보, 완료 화면 {Activity}
 */


class SignUpInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_info)
        keyBoardCheck()
        onBackPressed()

        boy_btn.setOnClickListener {
            boyClicked()
        }

        girl_btn.setOnClickListener {
            girlClicked()
        }

        date_picker.setOnClickListener {
            datePicker()
        }

        birt_rel.setOnClickListener {
            datePicker()
        }

        next_btn.setOnClickListener {
            startService()
        }
    }

    fun CloseKeyboard()
    {
        var view = this.currentFocus

        if(view != null)
        {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun keyBoardCheck(){
        TedKeyboardObserver(this).listen(object : BaseKeyboardObserver.OnKeyboardListener {
            override fun onKeyboardChange(isShow: Boolean) {
                if(!isShow){
                    keyboard_index.visibility = View.GONE
                    first_key.visibility = View.VISIBLE
                }
                else{
                    keyboard_index.visibility = View.VISIBLE
                    first_key.visibility = View.GONE
                }
            }
        })
    }

    fun boyClicked(){
        val pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("sex_signup",true)
        boy_btn.setBackgroundResource(R.drawable.signup_btn)
        boy_btn.setTextColor(Color.WHITE)
        girl_btn.setTextColor(Color.BLACK)
        girl_btn.setBackgroundResource(R.drawable.textinput_login)
        CloseKeyboard()
    }

    fun girlClicked(){
        val pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("sex_signup",false)
        girl_btn.setTextColor(Color.WHITE)
        boy_btn.setTextColor(Color.BLACK)
        girl_btn.setBackgroundResource(R.drawable.signup_btn)
        boy_btn.setBackgroundResource(R.drawable.textinput_login)
        CloseKeyboard()
    }

    fun datePicker(){
        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var secondDay = ""

        var date_listener  = object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                if(dayOfMonth.toString().length == 1){
                    secondDay = "0"+dayOfMonth
                }
                else{
                    secondDay = dayOfMonth.toString()
                }
                birth_tv.text = "${year}-0${month+1}-${secondDay}"
            }
        }

        var builder = DatePickerDialog(this, date_listener, year, month, day)
        builder.show()
    }

    fun startService(){
        val pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
        val editor = pref.edit()
        if(ph_tv.text.toString() != "" && birth_tv.text.toString() != "" && email_tv.text.toString() != ""  && name_tv.text.toString() != ""){
            editor.putString("name_signup", name_tv.text.toString())
            editor.putString("birth_signup", birth_tv.text.toString())
            editor.putString("mail_signup", email_tv.text.toString())
            editor.putString("phone_signup", ph_tv.text.toString())
            editor.commit()
            editor.apply()
            System.out.println("LOGD: " + pref.getString("name_signup","") + pref.getString("birth_signup","") + pref.getString("mail_signup","") + pref.getString("sex_signup",""))
            signup()
        }

        else{
            toast("입력란을 전부 입력해주세요.")
        }
    }


    fun signup(){
        val pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
        val editor = pref.edit()
        Client.retrofitService.logUp(
            pref.getString("name_signup",""),
            pref.getString("id_signup",""),
            pref.getString("pw_signup",""),
            pref.getString("phone_signup",""),
            pref.getString("birth_signup",""),
            pref.getString("mail_signup",""),
            pref.getBoolean("sex_signup", false)
        )
            .enqueue(object : Callback<Users> {
                override fun onResponse(call: Call<Users>?, response: Response<Users>?) {
                    when (response!!.code()) {
                        200 -> {
                            editor.putString("uuid", response.body()!!.uuid)
                            editor.putString("profile", response.body()!!.profile.toString())
                            editor.putString("userName", response.body()!!.name.toString())
                            editor.apply()
                            startActivity<SignUpFinishActivity>()
                            finish()
                        }
                        409 -> {
                            Toast.makeText(
                                this@SignUpInfoActivity,
                                "중복된 회원입니다.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        500 -> Toast.makeText(this@SignUpInfoActivity, "서버에러", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Users>?, t: Throwable?) {

                }
            })
    }


    override fun onBackPressed() {
        //super.onBackPressed()
    }
}