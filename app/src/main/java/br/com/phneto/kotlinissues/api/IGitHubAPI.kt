package br.com.phneto.kotlinissues.api

import br.com.phneto.kotlinissues.model.Issue
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface IGitHubAPI {

    @GET("issues")
    fun getAllIssuesObservable(@Query("state") state : String, @Query("filter") filter : String = "all"): Observable<List<Issue>>
}