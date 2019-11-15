package br.com.phneto.kotlinissues.api

import br.com.phneto.kotlinissues.model.Issue
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class GitHubIssuesService {

    private val gitHubAPI: IGitHubAPI = RetrofitConfiguration.getInstance().getGitHubApi()

    fun getAllIssues(state : String): Observable<List<Issue>>? {
        return gitHubAPI.getAllIssuesObservable(state)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }


}