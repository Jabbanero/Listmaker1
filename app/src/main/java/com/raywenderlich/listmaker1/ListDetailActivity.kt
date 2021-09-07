package com.raywenderlich.listmaker1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.raywenderlich.listmaker1.databinding.ListDetailActivityBinding
import com.raywenderlich.listmaker1.models.TaskList
import com.raywenderlich.listmaker1.ui.detail.ListDetailFragment
import com.raywenderlich.listmaker1.ui.detail.ListDetailViewModel
import com.raywenderlich.listmaker1.ui.main.MainViewModel
import com.raywenderlich.listmaker1.ui.main.MainViewModelFactory

class ListDetailActivity : AppCompatActivity() {

    lateinit var binding: ListDetailActivityBinding
    lateinit var viewModel: MainViewModel
    lateinit var fragment: ListDetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inflate layout using binding
        binding = ListDetailActivityBinding.inflate(layoutInflater)

        //set view with root view
        val view = binding.root
        setContentView(view)

        //add click listener
        binding.addTaskButton.setOnClickListener {
            showCreateTaskDialog()
        }

        //acquire viewModel from Intent
        viewModel = ViewModelProvider(
            this, MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(this))
        )
            .get(MainViewModel::class.java)


        viewModel.list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)!!

        //assign list name to Activity title
        title = viewModel.list.name
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.detail_container, ListDetailFragment.newInstance())
                .commitNow()
        }
    }

    private fun showCreateTaskDialog() {
        //create EditText
        val taskEditText = EditText(this)
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT

        //create Alert Builder
        AlertDialog.Builder(this)
            .setTitle(R.string.task_to_add)
            .setView(taskEditText)

            //click listener
            .setPositiveButton(R.string.add_task) { dialog, _ ->
                //create task from input
                val task = taskEditText.text.toString()
                //notify ViewModel of new item
                viewModel.addTask(task)
                //close dialog
                dialog.dismiss()
            }

            //create and show alert dialog
            .create()
            .show()
    }

    override fun onBackPressed() {
        //create and fill bundle
        val bundle = Bundle()
        bundle.putParcelable(MainActivity.INTENT_LIST_KEY, viewModel.list)

        //put bundle in Intent as extras
        val intent = Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }
}