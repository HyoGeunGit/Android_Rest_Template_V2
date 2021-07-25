package com.shimhg02.android_register_template.ui.Activity.SignUp


import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.shimhg02.android_register_template.R
import com.shimhg02.android_register_template.network.Retrofit.Client
import com.shimhg02.android_register_template.ui.Activity.Main.MainActivity
import kotlinx.android.synthetic.main.activity_signup_finish.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @description 회원가입 마무리(프로필 설정) 화면 {Activity}
 */


class SignUpFinishActivity : AppCompatActivity() {

    val PREFERENCE = "com.shimhg02.template"

    private val PERMISSION_REQUEST_CODE: Int = 101
    val REQUEST_IMAGE_CAPTURE = 1
    private var mCurrentPhotoPath: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_finish)
        val pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
        onBackPressed()
        if(pref.getBoolean("sex_signup",false) == false){
            profile_img.setImageResource(R.drawable.profile_female)
        }
        else{
            profile_img.setImageResource(R.drawable.profile_male)
        }

        profile_img.setOnClickListener {
            if (checkPersmission()) takePicture() else requestPermission()
        }

        next_btn.setOnClickListener {
            sendProfileImg(pref.getString("path","").toString())
        }


    }

    private fun takePicture() {

        val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file: File = createFile()

        val uri: Uri = FileProvider.getUriForFile(
            this,
            "com.shimhg02.android_register_template.fileprovider",
            file
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    takePicture()

                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }

            else -> {
            }
        }
    }


    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE, CAMERA), PERMISSION_REQUEST_CODE)
    }

    fun sendProfileImg(path : String){
        val file = File(path)
        val pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
        System.out.println("path: " + path)
        var fileName = pref.getString("name","")!!.replace("@","").replace(".","")
        fileName += ".png"
        var requestBody : RequestBody = RequestBody.create(MediaType.parse("image/*"),file)
        var body : MultipartBody.Part = MultipartBody.Part.createFormData("image",fileName,requestBody)

        System.out.println("uuid: " + pref.getString("uuid","") + " imagePath: " + body)

        Client.retrofitService.profileImgAdd(pref.getString("uuid","").toString(), body).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                    when (response!!.code()) {
                        200 -> {
                            startActivity<MainActivity>()
                            finish()
                        }
                        409 -> {
                            toast("No User Found")
                        }
                        500 -> {
                            toast("서버 에러입니다.")
                        }
                    }
                }

                override fun onFailure(call: Call<Void>?, t: Throwable?) {

                }
            })
    }


    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
        val editor = pref.edit()
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            //To get the File for further usage
            val auxFile = File(mCurrentPhotoPath)
            editor.putString("path", mCurrentPhotoPath)
            editor.apply()
            var bitmap: Bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath)
            profile_img.setImageBitmap(bitmap)
        }
    }

    @Throws(IOException::class)
    private fun createFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}