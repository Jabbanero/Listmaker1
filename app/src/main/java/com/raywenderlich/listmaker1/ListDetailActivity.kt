package com.raywenderlich.listmaker1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raywenderlich.listmaker1.models.TaskList
import com.raywenderlich.listmaker1.ui.detail.ListDetailFragment

class ListDetailActivity : AppCompatActivity() {

    lateinit var list: TaskList


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_detail_activity)
        //use LIST_KEY to reference Intent list
        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)!!
        //assign list name to Activity title
        title = list.name
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListDetailFragment.newInstance())
                .commitNow()
        }
    }
}