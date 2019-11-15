package br.com.phneto.kotlinissues.api

import br.com.phneto.kotlinissues.model.Issue
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject


class GitHubIssuesService : KoinComponent {

    private val githubIssueDataSource: GithubIssueRemoteDataSource by inject()

    fun getAllIssues(state: String): Observable<List<Issue>>? {
        return githubIssueDataSource.getAllIssuesObservable(state)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}