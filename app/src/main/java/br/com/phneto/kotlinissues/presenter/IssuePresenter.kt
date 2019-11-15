package br.com.phneto.kotlinissues.presenter

import br.com.phneto.kotlinissues.api.GitHubIssuesService
import br.com.phneto.kotlinissues.contracts.IssueContract
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject


class IssuePresenter(val view: IssueContract.View) : IssueContract.Presenter, KoinComponent {

    private val gitHubIssuesService: GitHubIssuesService by inject()

    private val compositeDisposable = CompositeDisposable()

    override fun retrieveIssues(state: String) {
        val subscribe = gitHubIssuesService.getAllIssues(state)?.subscribe({ issueList ->
            view.updateList(issueList)
        }, { error ->
            error.printStackTrace()
            view.errorResponse(error.message.toString())
        })

        if (subscribe != null) {
            compositeDisposable.add(subscribe)
        }
    }

    override fun viewDestroyed() {
        compositeDisposable.clear()
    }

}