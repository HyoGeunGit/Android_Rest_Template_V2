package com.shimhg02.android_register_template.network.Api


import com.shimhg02.android_register_template.network.Data.Users.Users
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @description API 관리 인터페이스
 */


interface API {

    @POST("/auth/signin")
    @FormUrlEncoded
    fun logIn(@Field("id") id : String,
              @Field("password") pw : String
    ):  Call<Users>

    @GET("/auth")
    fun autoLogin(
        @Header("uuid") uuid : String
    ):  Call<Users>

    @POST("/auth")
    @FormUrlEncoded
    fun logUp(@Field("name") name: String?,
              @Field("id") id: String?,
              @Field("password") pw: String?,
              @Field("phone") phoneNum: String?,
              @Field("birth") birth: String?,
              @Field("email") email: String?,
              @Field("gender") sex: Boolean?
    ): Call<Users>

    @GET("/auth/id/check")
    fun duplicate(@Query("id") id  : String) :  Call<Void>

    @PUT("/auth")
    @FormUrlEncoded
    fun fixPassword(@Field("uuid") uuid : String,
                    @Field("password") pw : String,
                    @Field("changePassword") chpw: String
    ): Call<Void>

    @PUT("/auth")
    @FormUrlEncoded
    fun fixName(@Field("uuid") uuid : String,
                @Field("name") name: String
    ): Call<Void>


    @FormUrlEncoded
    @HTTP(method="DELETE", hasBody=true, path="/auth")
    fun delUser(@Field("uuid") uuid : String): Call<Void>


    @Multipart
    @POST("/auth/profile")
    fun profileImgAdd(
        @Part("uuid") uuid : String,
        @Part image : MultipartBody.Part
    ): Call<Void>
}