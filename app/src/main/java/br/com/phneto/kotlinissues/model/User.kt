package br.com.phneto.kotlinissues.model

import java.io.Serializable

data class User(val id: Long, val login: String, val avatar_url: String) : Serializable
