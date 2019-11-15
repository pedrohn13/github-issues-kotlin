package br.com.phneto.kotlinissues.model

import java.io.Serializable
import java.util.Date

data class Issue(val id: Long, val title: String, val created_at: Date, val user: User, val state: String, val body: String, val html_url: String) : Serializable
