package br.com.phneto.kotlinissues.contracts

import br.com.phneto.kotlinissues.model.Issue

interface IssueContract {

    interface View {
        fun updateList(issues :List<Issue>)
        fun errorResponse(errorMessage : String)
    }

    interface Presenter {
        fun retrieveIssues(state : String)
        fun viewDestroyed()
    }
}