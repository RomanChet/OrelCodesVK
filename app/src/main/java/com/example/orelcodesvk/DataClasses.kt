package com.example.orelcodesvk

import android.graphics.Bitmap

// Определяем классы (и их характеристики)

data class Size(val width: Int, val url: String)

data class Photo(val date: Long, val sizes: Array<Size>)

data class Video(val title: String, val image: Array<Size>)

data class Attachment(val type: String, val photo: Photo?, val video: Video?)

data class Post(val text: String, val date: Long, val attachments: Array<Attachment>?, val copy_history: Array<Post>?)

data class Response(val count: Int, val items: Array<Post>)

data class GroupKeeper(val name: String, val photo: Bitmap?)

data class Group(val name: String, val photo_200: String)

data class GroupResponse(val response: Array<Group>)

data class Answer(val response: Response?)