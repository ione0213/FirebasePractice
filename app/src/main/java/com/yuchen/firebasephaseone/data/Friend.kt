package com.yuchen.firebasephaseone.data

data class Friend(
    val user_id: String,
    val friend_list: List<String>,
    val invitation_list: List<String>
)