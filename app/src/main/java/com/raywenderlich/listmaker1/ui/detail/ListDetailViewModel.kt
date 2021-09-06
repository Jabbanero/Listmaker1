package com.raywenderlich.listmaker1.ui.detail

import androidx.lifecycle.ViewModel
import com.raywenderlich.listmaker1.models.TaskList

class ListDetailViewModel() : ViewModel() {

    //inform Fragment of new task
    lateinit var onTaskAdded: (() -> Unit)
    lateinit var list: TaskList

    //add tasks to list, invoke onTaskAdded
    fun addTask(task: String) {
        list.tasks.add(task)
        onTaskAdded.invoke()
    }
}