package br.com.phneto.kotlinissues.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.phneto.kotlinissues.R
import br.com.phneto.kotlinissues.model.Issue
import br.com.phneto.kotlinissues.view.holder.IssueItemListHolder

class IssueListAdapter(private val dataSet: List<Issue>) : RecyclerView.Adapter<IssueItemListHolder>() {

    override fun onCreateViewHolder(view: ViewGroup, i: Int): IssueItemListHolder =
        IssueItemListHolder(
            LayoutInflater.from(view.context).inflate(
                R.layout.issue_item_list,
                view,
                false
            )
        )


    override fun getItemCount() = dataSet.count()

    override fun onBindViewHolder(lineHolder: IssueItemListHolder, index: Int) {
        lineHolder.setItemFields(dataSet[index])
    }
}