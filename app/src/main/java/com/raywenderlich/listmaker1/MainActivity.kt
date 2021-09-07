package com.raywenderlich.listmaker1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.raywenderlich.listmaker1.databinding.MainActivityBinding
import com.raywenderlich.listmaker1.models.TaskList
import com.raywenderlich.listmaker1.ui.detail.ListDetailFragment
import com.raywenderlich.listmaker1.ui.main.MainFragment
import com.raywenderlich.listmaker1.ui.main.MainViewModel
import com.raywenderlich.listmaker1.ui.main.MainViewModelFactory

class MainActivity : AppCompatActivity(), MainFragment.MainFragmentInteractionListener {

    private lateinit var binding: MainActivityBinding
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this, MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(this)))
            .get(MainViewModel::class.java)

        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            //create mainFragment instance as clickListener
            val mainFragment = MainFragment.newInstance()
            mainFragment.clickListener = this

            //create and assign container id
            val fragmentContainerViewId: Int = if (binding.mainFragmentContainer == null) {
                R.id.detail_container
            } else {
                R.id.main_fragment_container
            }

            //add mainFragment to container view
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(fragmentContainerViewId, mainFragment)
            }
        }

        binding.fabButton.setOnClickListener {
            showCreateListDialog()
        }
    }

    private fun showCreateListDialog() {

        //retrieve strings
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)

        //create alert dialog
        val builder = AlertDialog.Builder(this)
        val listTitleEditText = EditText(this)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)

        //add positive button
        builder.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            dialog.dismiss()
            //create a new list
            val taskList = TaskList(listTitleEditText.text.toString())
            viewModel.saveList(taskList)
            showListDetail(taskList)
        }
        //display dialog
        builder.create().show()
    }

    private fun showListDetail(list: TaskList) {
        if(binding.mainFragmentContainer == null) {
            //create the intent
            val listDetailIntent = Intent(this, ListDetailActivity::class.java)
            //pass list and key as extras
            listDetailIntent.putExtra(INTENT_LIST_KEY, list)
            //start the intent activity
            startActivityForResult(listDetailIntent, LIST_DETAIL_REQUEST_CODE)
        } else{
            val bundle = bundleOf(INTENT_LIST_KEY to list)
            supportFragmentManager.commit{
                setReorderingAllowed(true)
                replace(R.id.list_detail_fragment_container, ListDetailFragment::class.java, bundle, null)
            }
            binding.fabButton.setOnClickListener {
                showCreateTaskDialog()
            }
        }
    }

    //used by Intent to refer to a list
    companion object {
        const val INTENT_LIST_KEY = "list"
        const val LIST_DETAIL_REQUEST_CODE = 123
    }

    //send list
    override fun listItemTapped(list: TaskList) {
        showListDetail(list)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //check request code
        if (requestCode == LIST_DETAIL_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //unwrap Intent
            data?.let {
                //save list to viewModel
                viewModel.updateList(data.getParcelableExtra(INTENT_LIST_KEY)!!)
                viewModel.refreshLists()
            }
        }
    }

    private fun showCreateTaskDialog() {
        val taskEditText = EditText(this)
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT
        AlertDialog.Builder(this)
            .setTitle(R.string.task_to_add)
            .setView(taskEditText)
            .setPositiveButton(R.string.add_task) { dialog, _ ->
                val task = taskEditText.text.toString()
                viewModel.addTask(task)
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onBackPressed() {
        //acquire fragment
        val listDetailFragment =
            supportFragmentManager.findFragmentById(R.id.list_detail_fragment_container)
        //close activity if null
        if (listDetailFragment == null) {
            super.onBackPressed()
        } else {
            //reset title
            title = resources.getString(R.string.app_name)
            //remove ListDetailFragment
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                remove(listDetailFragment)
            }
            //reset FAB
            binding.fabButton.setOnClickListener {
                showCreateListDialog()
            }
        }
    }
}