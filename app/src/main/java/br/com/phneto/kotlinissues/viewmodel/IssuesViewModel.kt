package br.com.phneto.kotlinissues.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.phneto.kotlinissues.api.GitHubIssuesService
import br.com.phneto.kotlinissues.model.Issue
import br.com.phneto.kotlinissues.util.Constants
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class IssuesViewModel : ViewModel(), KoinComponent {

    val issuesLiveData: MutableLiveData<List<Issue>> = MutableLiveData()
    val statusResponseLiveData: MutableLiveData<String> = MutableLiveData()

    private val gitHubIssuesService: GitHubIssuesService by inject()

    private val compositeDisposable = CompositeDisposable()

    fun retrieveIssues(state: String) {
        val subscribe = gitHubIssuesService.getAllIssues(state)?.subscribe({ issueList ->
            issuesLiveData.value = issueList
            statusResponseLiveData.value = Constants.OK
        }, { error ->
            error.printStackTrace()
            statusResponseLiveData.value = Constants.FAIL
        })

        if (subscribe != null) {
            compositeDisposable.add(subscribe)
        }
    }

}