package br.com.phneto.kotlinissues.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.phneto.kotlinissues.R
import br.com.phneto.kotlinissues.model.Issue
import br.com.phneto.kotlinissues.model.IssueState
import br.com.phneto.kotlinissues.util.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_issue_detail.*
import java.text.DateFormat
import java.util.*


class IssueDetailActivity : AppCompatActivity() {

    private lateinit var issue: Issue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_detail)

        issue = intent.getSerializableExtra(Constants.ISSUE_KEY) as Issue

        loadTexts()
        loadAvatar()
        loadButton()
    }

    private fun loadButton() {
        btnGoToGithub.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(issue.html_url)
            startActivity(i)
        }
    }

    private fun loadAvatar() {
        Picasso.get().load(issue.user.avatar_url).into(userAvatar)
        setStateColor(issue.state)
    }

    private fun loadTexts() {
        txtState.text = issue.state.capitalize()
        txtUserName.text = issue.user.login
        txtTitleDetail.text = issue.title
        txtMessageDetail.text = if (issue.body == "") "No description given" else issue.body

        val f = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault())
        txtDate.text = f.format(Date())
    }

    private fun setStateColor(state: String) {
        when (state) {
            IssueState.OPEN.state -> txtState.setBackgroundResource(R.color.stateOpen)
            IssueState.CLOSED.state -> txtState.setBackgroundResource(R.color.stateClosed)
        }
    }
}
