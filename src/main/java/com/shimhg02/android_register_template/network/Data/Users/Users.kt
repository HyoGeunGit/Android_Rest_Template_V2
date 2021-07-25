package com.shimhg02.android_register_template.network.Data.Users

/**
 * @description 유저 데이터 클래스 {data class}
 */

data class Users(
    val name: String,
    val userID: String,
    val phone: String,
    val birth: String,
    val email: String,
    val nickName: String,
    val sex: String,
    val profile: String,
    val uuid: String
)