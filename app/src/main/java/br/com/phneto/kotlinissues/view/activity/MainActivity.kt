package br.com.phneto.kotlinissues.view.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.phneto.kotlinissues.R
import br.com.phneto.kotlinissues.contracts.IssueContract
import br.com.phneto.kotlinissues.model.Issue
import br.com.phneto.kotlinissues.model.IssueState
import br.com.phneto.kotlinissues.presenter.IssuePresenter
import br.com.phneto.kotlinissues.view.adapter.IssueListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), IssueContract.View {

    val issuePresenter: IssuePresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (checkConnection()) {
            loadListView()
            setRadioListener()
            retreiveIssueList(IssueState.ALL)
        } else {
            showDialogNoConnection()
        }
    }

    private fun showDialogNoConnection() {
        showErrorDialog(
            "No Internet Connection",
            "The App Cannot Load the Issues. Verify your connection or try later."
        )
    }

    private fun showErrorDialog(title: String, message: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)

        alertDialogBuilder.setPositiveButton(
            "Ok"
        ) { _, _ ->
            finish()
        }

        val alert = alertDialogBuilder.create()
        alert.show()
    }

    override fun updateList(issues: List<Issue>) {
        progressBar.visibility = View.INVISIBLE
        listIssue.adapter = IssueListAdapter(issues)
        toggleRadios(true)
    }

    override fun errorResponse(errorMessage: String) {
        progressBar.visibility = View.INVISIBLE
        showErrorDialog("Unexpected Error", "Try again Later")
    }

    private fun loadListView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        listIssue.setHasFixedSize(true)
        listIssue.layoutManager = layoutManager
    }

    private fun setRadioListener() {
        rdFilter.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            when (radio.text.toString().toLowerCase()) {
                IssueState.ALL.state -> retreiveIssueList(IssueState.ALL)
                IssueState.OPEN.state -> retreiveIssueList(IssueState.OPEN)
                IssueState.CLOSED.state -> retreiveIssueList(IssueState.CLOSED)
            }
        }
    }

    private fun retreiveIssueList(issueState: IssueState) {
        toggleRadios(false)
        progressBar.visibility = View.VISIBLE
        issuePresenter.retrieveIssues(issueState.state)
    }

    private fun toggleRadios(enabled: Boolean) {
        for (i in 0 until rdFilter.childCount) {
            (rdFilter.getChildAt(i) as RadioButton).isEnabled = enabled
        }
    }

    private fun checkConnection(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
    }
}
