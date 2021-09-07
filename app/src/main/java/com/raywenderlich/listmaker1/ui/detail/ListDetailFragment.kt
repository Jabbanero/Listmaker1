package com.raywenderlich.listmaker1.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.raywenderlich.listmaker1.MainActivity
import com.raywenderlich.listmaker1.R
import com.raywenderlich.listmaker1.databinding.ListDetailFragmentBinding
import com.raywenderlich.listmaker1.models.TaskList
import com.raywenderlich.listmaker1.ui.main.MainViewModel
import com.raywenderlich.listmaker1.ui.main.MainViewModelFactory

class ListDetailFragment : Fragment() {

    lateinit var binding: ListDetailFragmentBinding

    companion object {
        fun newInstance() = ListDetailFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        //create and return viewBinding
        binding = ListDetailFragmentBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(PreferenceManager.
            getDefaultSharedPreferences(requireActivity()))).get(MainViewModel::class.java)

        //get list and assign to viewmodel, set activity title
        val list: TaskList? = arguments?.getParcelable(MainActivity.INTENT_LIST_KEY)
        if (list != null) {
            viewModel.list = list
            requireActivity().title = list.name
        }

        //create and assign adapter and layoutManager
        val recyclerAdapter = ListItemsRecyclerViewAdapter(viewModel.list)
        binding.listItemsRecyclerview.adapter = recyclerAdapter
        binding.listItemsRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        //assign callback to viewmodel lambda
        viewModel.onTaskAdded = {
            recyclerAdapter.notifyDataSetChanged()
        }


    }

}