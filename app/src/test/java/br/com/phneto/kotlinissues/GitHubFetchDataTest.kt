package br.com.phneto.kotlinissues

import appModule
import br.com.phneto.kotlinissues.api.GithubIssueRemoteDataSource
import br.com.phneto.kotlinissues.model.Issue
import br.com.phneto.kotlinissues.model.IssueState
import br.com.phneto.kotlinissues.model.User
import io.reactivex.Observable
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito.given
import java.util.*

class GitHubFetchDataTest : AutoCloseKoinTest() {

    private lateinit var data: List<Issue>
    private val totalOpenIssues = 5
    private val totalClosedIssues = 15

    private val dataResurce: GithubIssueRemoteDataSource by inject()

    @Before
    fun before() {
        data = createIssueList()
        startKoin {
            modules(appModule)
        }
        declareMock<GithubIssueRemoteDataSource> {
            given(this.getAllIssuesObservable(IssueState.OPEN.state)).willReturn(Observable.just(data.filter { it.state == IssueState.OPEN.state }))
            given(this.getAllIssuesObservable(IssueState.CLOSED.state)).willReturn(Observable.just(data.filter { it.state == IssueState.CLOSED.state }))
            given(this.getAllIssuesObservable(IssueState.ALL.state)).willReturn(Observable.just(data))
        }
    }

    @Test
    fun testFetchAllIssues() {
        val result = dataResurce.getAllIssuesObservable(IssueState.ALL.state).test()
        assertEquals(totalOpenIssues + totalClosedIssues, result.values().first().size)
    }

    @Test
    fun testFetchOpenIssues() {
        val result = dataResurce.getAllIssuesObservable(IssueState.OPEN.state).test()
        assertEquals(totalOpenIssues, result.values().first().size)
    }

    @Test
    fun testFetchClosedIssues() {
        val result = dataResurce.getAllIssuesObservable(IssueState.CLOSED.state).test()
        assertEquals(totalClosedIssues, result.values().first().size)
    }

    private fun createIssueList(): List<Issue> {
        var listIssues = ArrayList<Issue>()
        for (i in 0 until totalOpenIssues) listIssues.add(createIssue(IssueState.OPEN))
        for (i in 0 until totalClosedIssues) listIssues.add(createIssue(IssueState.CLOSED))
        return listIssues
    }

    private fun createIssue(issueState: IssueState): Issue {
        return Issue(1, 0, "", Date(), User(0, "", ""), issueState.state, "", "")
    }
}