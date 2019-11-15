package br.com.phneto.kotlinissues.api

import br.com.phneto.kotlinissues.model.Issue
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GithubIssueRemoteDataSource : IGitHubAPI {

    private val baseURL = "https://api.github.com/repos/JetBrains/kotlin/"

    private var api : IGitHubAPI

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        this.api = retrofit.create(IGitHubAPI::class.java)
    }
    override fun getAllIssuesObservable(state: String, filter: String): Observable<List<Issue>> {
        return this.api.getAllIssuesObservable(state, filter)
    }
}