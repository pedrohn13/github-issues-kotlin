package br.com.phneto.kotlinissues.view.holder

import android.content.Intent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.phneto.kotlinissues.R
import br.com.phneto.kotlinissues.model.Issue
import br.com.phneto.kotlinissues.model.IssueState
import br.com.phneto.kotlinissues.util.Constants
import br.com.phneto.kotlinissues.view.activity.IssueDetailActivity
import java.text.DateFormat
import java.util.*

class IssueItemListHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    private val txtIssueNum = view.findViewById<TextView>(R.id.txtIssueNum)
    private val txtState = view.findViewById<TextView>(R.id.txtState)
    private val txtTitle = view.findViewById<TextView>(R.id.txtTitle)
    private val txtDescription = view.findViewById<TextView>(R.id.txtDescription)
    private val areaState = view.findViewById<RelativeLayout>(R.id.areaState)
    private val issueItemList = view.findViewById<RelativeLayout>(R.id.issueItemList)
    private val context = view.context

    private lateinit var mIssue: Issue

    fun setItemFields(issue: Issue) {
        mIssue = issue
        txtIssueNum.text = context.getString(R.string.issue_num, issue.number.toString())
        txtState.text = issue.state.capitalize()
        txtTitle.text = issue.title
        val f = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault())
        txtDescription.text = context.getString(R.string.issue_description, issue.user.login, f.format(Date()))
        issueItemList.setOnClickListener(this)
        setStateColor(issue.state)
    }

    private fun setStateColor(state: String) {
        when (state) {
            IssueState.OPEN.state -> areaState.setBackgroundResource(R.color.stateOpen)
            IssueState.CLOSED.state -> areaState.setBackgroundResource(R.color.stateClosed)
        }
    }

    override fun onClick(v: View?) {
        val intent = Intent(context, IssueDetailActivity::class.java)
        intent.putExtra(Constants.ISSUE_KEY, mIssue)
        context.startActivity(intent)
    }
}