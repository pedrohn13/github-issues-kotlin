package br.com.phneto.kotlinissues.view.holder

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.phneto.kotlinissues.R
import br.com.phneto.kotlinissues.model.Issue
import br.com.phneto.kotlinissues.model.IssueState
import br.com.phneto.kotlinissues.util.Constants
import br.com.phneto.kotlinissues.view.activity.IssueDetailActivity
import kotlinx.android.synthetic.main.issue_item_list.view.*
import java.text.DateFormat
import java.util.*

class IssueItemListHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    private val txtIssueNum = view.txtIssueNum
    private val txtState = view.txtState
    private val txtTitle = view.txtTitle
    private val txtDescription = view.txtDescription
    private val areaState = view.areaState
    private val issueItemList = view.issueItemList
    private val context = view.context

    private lateinit var mIssue: Issue

    fun setItemFields(issue: Issue) {
        mIssue = issue
        txtIssueNum.text = context.getString(R.string.issue_num, issue.number.toString())
        txtState.text = issue.state.capitalize()
        txtTitle.text = issue.title

        val f = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault())
        txtDescription.text =
            context.getString(R.string.issue_description, issue.user.login, f.format(issue.created_at))

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