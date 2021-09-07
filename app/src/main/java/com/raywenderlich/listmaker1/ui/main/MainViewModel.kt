package com.raywenderlich.listmaker1.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.raywenderlich.listmaker1.models.TaskList

class MainViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    //used to inform other classes when a list is added
    lateinit var onListAdded: (() -> Unit)
    lateinit var list: TaskList
    lateinit var onTaskAdded: (() -> Unit)

    fun addTask(task: String) {
        list.tasks.add(task)
        onTaskAdded.invoke()
    }

    //create an empty list. when called, list is populated with retrieveLists()
    val lists: MutableList<TaskList> by lazy {
        retrieveLists()
    }

    //retrieve TaskLists from SharedPreferences.
    private fun retrieveLists(): MutableList<TaskList> {
        val sharedPreferencesContents = sharedPreferences.all
        val taskLists = ArrayList<TaskList>()

        //add each TaskList to taskLists as hash sets
        for (taskList in sharedPreferencesContents) {
            val itemsHashSet = ArrayList(taskList.value as HashSet<String>)
            val list = TaskList(taskList.key, itemsHashSet)
            taskLists.add(list)
        }
        return taskLists
    }

    fun saveList(list: TaskList) {
        sharedPreferences.edit().putStringSet(list.name, list.tasks.toHashSet()).apply()
        lists.add(list)
        onListAdded.invoke()    //inform other classes to the change
    }

    //write list to SharedPreferences
    fun updateList(list: TaskList) {
        sharedPreferences.edit().putStringSet(list.name, list.tasks.toHashSet()).apply()
        lists.add(list)
    }

    //clear list, retrieve and add new list
    fun refreshLists() {
        lists.clear()
        lists.addAll(retrieveLists())
    }


}