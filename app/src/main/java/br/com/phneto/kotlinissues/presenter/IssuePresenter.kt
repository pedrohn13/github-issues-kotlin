package br.com.phneto.kotlinissues.presenter

import br.com.phneto.kotlinissues.api.GitHubIssuesService
import br.com.phneto.kotlinissues.contracts.IssueContract
import br.com.phneto.kotlinissues.model.Issue
import org.koin.core.KoinComponent
import org.koin.core.inject

class IssuePresenter(val view: IssueContract.View) : IssueContract.Presenter, KoinComponent {

    private val gitHubIssuesService: GitHubIssuesService by inject()

    override fun retrieveIssues(state: String) {
        gitHubIssuesService.getAllIssues(state)?.subscribe(this::handleResponse, this::handleError)
    }

    private fun handleResponse(issueList: List<Issue>) {
        view.updateList(issueList)
    }

    private fun handleError(error: Throwable) {
        error.printStackTrace()
        view.errorResponse(error.message.toString())
    }

}