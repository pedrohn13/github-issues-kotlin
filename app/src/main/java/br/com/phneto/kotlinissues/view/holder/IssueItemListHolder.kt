package br.com.phneto.kotlinissues.view.holder

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import br.com.phneto.kotlinissues.R
import br.com.phneto.kotlinissues.model.Issue
import br.com.phneto.kotlinissues.model.IssueState
import br.com.phneto.kotlinissues.util.Constants
import br.com.phneto.kotlinissues.view.activity.IssueDetailActivity

class IssueItemListHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    private val txtState = view.findViewById<TextView>(R.id.txtState)
    private val txtTitle = view.findViewById<TextView>(R.id.txtTitle)
    private val areaState = view.findViewById<RelativeLayout>(R.id.areaState)
    private val issueItemList = view.findViewById<RelativeLayout>(R.id.issueItemList)
    private val context = view.context

    private lateinit var mIssue : Issue

    fun setItemFields(issue: Issue) {
        mIssue = issue
        txtState.text = issue.state.capitalize()
        txtTitle.text = issue.title
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