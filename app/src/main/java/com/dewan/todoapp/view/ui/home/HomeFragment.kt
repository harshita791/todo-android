package com.dewan.todoapp.view.ui.home


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.dewan.todoapp.R
import com.dewan.todoapp.databinding.HomeFragmentBinding
import com.dewan.todoapp.model.remote.response.todo.TaskResponse
import com.dewan.todoapp.view.adaptor.TaskAdapter
import com.dewan.todoapp.view.adaptor.TaskCallBack
import com.dewan.todoapp.viewmodel.home.HomeViewModel

class HomeFragment : Fragment(), TaskCallBack{

    companion object {
        fun newInstance() = HomeFragment()
        const val TAG = "HomeFragment"
    }

    private lateinit var viewModel: HomeViewModel
    private var taskList : ArrayList<TaskResponse> = ArrayList()
    private lateinit var binding: HomeFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.home_fragment,container,false)

        //Recycler view
        recyclerView = binding.taskRecyclerView
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.init(activity?.applicationContext!!)
        //get all task
        getAllTask()
    }

    private fun getAllTask(){
        viewModel.getAllTask().observe(viewLifecycleOwner, Observer {
            if(it.code()==200){

                //clear data from taskList
                taskList.clear()
                //add data to taskList
                taskList = it.body()!!.toCollection(taskList)
                setRecyclerView()
              /*  for (task in taskList){
                    Log.e(TAG,"title: ${task.title} body: ${it.body()}")
                }*/
            }
            else{
                Log.e(TAG,"error code : ${it.code()} error message : ${it.errorBody()}")
            }
        })
    }

    private fun setRecyclerView(){
        taskAdapter = TaskAdapter(taskList)
        recyclerView.adapter = taskAdapter
        taskAdapter.setTaskCallback(this)
    }

    override fun onTaskClick(view: View, position: Int, isLongclick: Boolean) {

        if(isLongclick){
            Log.e(TAG,"position: $position is a long click")
        }
        else{
            val data = viewModel.taskList.value?.get(position)

            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToTaskDetailFragment(
                data?.createdAt.toString(),
                data?.title.toString(),
                data?.body.toString(),
                data?.status.toString(),
                data?.userId.toString(),
                data?.bg_color.toString(),
                data?.id.toString()))
            Log.e(TAG,"position: $position is a single click")
        }
    }
}
