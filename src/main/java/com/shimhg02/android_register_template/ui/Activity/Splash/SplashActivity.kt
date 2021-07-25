package com.shimhg02.android_register_template.ui.Activity.Splash


import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.shimhg02.android_register_template.R
import com.shimhg02.android_register_template.network.Data.Users.Users
import com.shimhg02.android_register_template.network.Retrofit.Client
import com.shimhg02.android_register_template.ui.Activity.Main.MainActivity
import com.shimhg02.android_register_template.ui.Activity.SignIn.SignInActivity
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val PREFERENCE = "com.shimhg02.template"

/**
 * @description 스플래시 화면 {Activity}
 */


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val handler = Handler()
        handler.postDelayed({
            AutoLogin()
        }, 2000)

    }

    private fun AutoLogin(){
        val pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
        val editor = pref.edit()
        Client.retrofitService.autoLogin(pref.getString("uuid","").toString()).enqueue(object :
            Callback<Users> {
            override fun onResponse(call: Call<Users>?, response: Response<Users>?) {
                when (response!!.code()) {
                    200 -> {
                        editor.putString("uuid", response.body()!!.uuid)
                        editor.putString("profile", response.body()!!.profile)
                        editor.putString("userName", response.body()!!.name)
                        editor.apply()
                        startActivity<MainActivity>()
                        finish()
                    }
                    404 ->  {
                        startActivity<SignInActivity>()
                        finish()
                    }
                    500 -> toast("서버 에러")
                }
            }
            override fun onFailure(call: Call<Users>?, t: Throwable?) {
            }
        })
    }
}

