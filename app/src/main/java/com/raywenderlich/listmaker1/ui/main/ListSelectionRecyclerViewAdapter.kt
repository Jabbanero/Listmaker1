package com.raywenderlich.listmaker1.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.listmaker1.models.TaskList
import com.raywenderlich.listmaker1.databinding.ListSelectionViewHolderBinding

class ListSelectionRecyclerViewAdapter(
    private val lists: MutableList<TaskList>,
    val clickListener: ListSelectionRecyclerViewClickListener
) :
    RecyclerView.Adapter<ListSelectionViewHolder>() {

    //interface to deal with tapped list items
    interface ListSelectionRecyclerViewClickListener {
        fun listItemClicked(list: TaskList)
    }

    //create and fill ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder {
        val binding = ListSelectionViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListSelectionViewHolder(binding)
    }

    //assign text to itemNumber and itemString
    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {
        holder.binding.itemNumber.text = (position + 1).toString()
        holder.binding.itemString.text = lists[position].name
        //add onClickListener
        holder.itemView.setOnClickListener {
            clickListener.listItemClicked(lists[position])
        }
    }

    //get list size
    override fun getItemCount(): Int {
        return lists.size
    }

    //inform Adapter the list has been updated
    fun listsUpdated() {
        notifyItemInserted(lists.size - 1)
    }


}