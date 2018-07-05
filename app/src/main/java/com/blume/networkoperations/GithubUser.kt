package com.blume.networkoperations

import android.graphics.Bitmap

data class GithubUser (
        val login :String,
        val id :Int,
        val html_url :String,
        val score :Double,
        val avatar_url :String
)