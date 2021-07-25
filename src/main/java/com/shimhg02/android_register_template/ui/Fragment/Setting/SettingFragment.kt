package com.shimhg02.android_register_template.ui.Fragment.Setting


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.shimhg02.android_register_template.R
import com.shimhg02.android_register_template.ui.Activity.ChangeName.ChangeNameActivity
import com.shimhg02.android_register_template.ui.Activity.ChangePw.ChangePasswordActivity
import com.shimhg02.android_register_template.ui.Activity.DelUser.DelUserActivity
import kotlinx.android.synthetic.main.fragment_setting.view.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * @description 세팅 화면 {Fragment}
 */


class SettingFragment : Fragment() {

    val PREFERENCE = "com.shimhg02.template"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        val pref = view.context.getSharedPreferences(PREFERENCE, AppCompatActivity.MODE_PRIVATE)

        Glide.with(view.context).asBitmap().load(pref.getString("profile","").toString()).diskCacheStrategy(
            DiskCacheStrategy.ALL).thumbnail(0.1f).into(view.profile_img)

        view.profile_text.text = pref.getString("userName","").toString()

        view.del_btn.setOnClickListener {
            startActivity<DelUserActivity>()
        }

        view.name_tv.setOnClickListener {
            startActivity<ChangeNameActivity>()
        }

        view.change_passwd_tv.setOnClickListener {
            startActivity<ChangePasswordActivity>()
        }

        return view
    }


}