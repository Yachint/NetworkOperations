package com.blume.networkoperations

data class ApiResult (
        val total_count : Int,
        val incomplete_results : Boolean,
        val items : ArrayList<GithubUser>
)