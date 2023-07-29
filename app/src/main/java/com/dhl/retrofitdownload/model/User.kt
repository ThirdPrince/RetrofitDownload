package com.dhl.retrofitdownload.model



/**
 * GitHubUser model
 * @author dhl
 */

data class User(
    val primaryKey: Long,
    val login: String,
    val id: Long,
    val avatar_url: String
)

data class Stargazer(
    val login: String,
    val id: Int,
    val avatar_url: String
)