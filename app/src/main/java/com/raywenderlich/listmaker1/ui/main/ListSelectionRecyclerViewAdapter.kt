package com.raywenderlich.listmaker1.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.listmaker1.TaskList
import com.raywenderlich.listmaker1.databinding.ListSelectionViewHolderBinding

class ListSelectionRecyclerViewAdapter(private val lists : MutableList<TaskList>) : RecyclerView.Adapter<ListSelectionViewHolder>() {

    //create and fill ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder {
        val binding = ListSelectionViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListSelectionViewHolder(binding)
    }

    //assign text to itemNumber and itemString
    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {
        holder.binding.itemNumber.text = (position + 1).toString()
        holder.binding.itemString.text = lists[position].name
    }

    //get list size
    override fun getItemCount(): Int {
        return lists.size
    }

    //inform Adapter the list has been updated
    fun listsUpdated(){
        notifyItemInserted(lists.size-1)
    }


}