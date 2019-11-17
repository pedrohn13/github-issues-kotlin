package br.com.phneto.kotlinissues.view.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.phneto.kotlinissues.R
import br.com.phneto.kotlinissues.model.Issue
import br.com.phneto.kotlinissues.model.IssueState
import br.com.phneto.kotlinissues.util.Constants
import br.com.phneto.kotlinissues.view.adapter.IssueListAdapter
import br.com.phneto.kotlinissues.viewmodel.IssuesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var issueViewModel: IssuesViewModel
    private var selectedState: IssueState = IssueState.ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setViewModel()
        setRefreshListener()
        setRadioListener()
        loadListView()

        if (checkConnection()) retrieveIssueList(selectedState) else showDialogNoConnection()
    }

    private fun setViewModel() {
        issueViewModel = ViewModelProviders.of(this).get(IssuesViewModel::class.java)
        with(issueViewModel) {
            issuesLiveData.observe(this@MainActivity, Observer {
                updateList(it)
            })
            statusResponseLiveData.observe(this@MainActivity, Observer {
                if (it == Constants.FAIL) {
                    errorResponse()
                }
            })
        }
    }

    private fun showDialogNoConnection() {
        toggleRadios(false)
        showErrorDialog(
            getString(R.string.no_internet_connection),
            getString(R.string.no_internet_message)
        )
    }

    private fun showErrorDialog(title: String, message: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(title)
            setMessage(message)
            setPositiveButton(
                getString(R.string.ok)
            ) { _, _ ->
                progressBar.visibility = View.INVISIBLE
                reloadArea.visibility = View.VISIBLE
                listIssue.visibility = View.INVISIBLE
            }
        }

        val alert = alertDialogBuilder.create()
        alert.show()
    }

    private fun updateList(issues: List<Issue>) {
        progressBar.visibility = View.INVISIBLE
        listIssue.visibility = View.VISIBLE
        listIssue.adapter = IssueListAdapter(issues)
        toggleRadios(true)
    }

    private fun errorResponse() {
        progressBar.visibility = View.INVISIBLE
        showErrorDialog(getString(R.string.error), getString(R.string.no_internet_message))
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
                IssueState.ALL.state -> selectedState = IssueState.ALL
                IssueState.OPEN.state -> selectedState = IssueState.OPEN
                IssueState.CLOSED.state -> selectedState = IssueState.CLOSED
            }
            retrieveIssueList(selectedState)
        }
    }

    private fun setRefreshListener() {
        refreshList.setOnClickListener {
            reloadArea.visibility = View.INVISIBLE
            retrieveIssueList(selectedState)
        }
    }

    private fun retrieveIssueList(issueState: IssueState) {
        toggleRadios(false)
        progressBar.visibility = View.VISIBLE
        issueViewModel.retrieveIssues(issueState.state)
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
