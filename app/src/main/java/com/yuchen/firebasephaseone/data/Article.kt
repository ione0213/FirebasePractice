package com.yuchen.firebasephaseone.data

import com.google.firebase.firestore.FieldValue

data class Article(
    val id: String,
    val title: String,
    val content: String,
    val tag: String,
    val author_id: String,
    val created_time: FieldValue
)