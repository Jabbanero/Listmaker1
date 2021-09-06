package com.raywenderlich.listmaker1.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.listmaker1.databinding.ListItemViewHolderBinding
import com.raywenderlich.listmaker1.models.TaskList

class ListItemsRecyclerViewAdapter(var list: TaskList) : RecyclerView.Adapter<ListItemViewHolder>() {

    //create view from binding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val binding =
            ListItemViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return list.tasks.size
    }


}