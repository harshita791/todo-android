package com.dewan.todoapp.view.ui.task

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.dewan.todoapp.R
import com.dewan.todoapp.model.remote.request.todo.AddTaskRequest
import com.dewan.todoapp.viewmodel.task.TaskViewModel
import kotlinx.android.synthetic.main.task_fragment.*
import kotlinx.android.synthetic.main.task_fragment.view.*
import org.jetbrains.anko.support.v4.alert

class TaskFragment : Fragment() {

    companion object{
        const val TAG = "TaskFragment"
        fun newInstance() = TaskFragment()
    }

    private lateinit var viewModel: TaskViewModel
    private val taskList: ArrayList<String> = ArrayList()
    private var userId: Int = 0
    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.task_fragment, container, false)

        resources.getStringArray(R.array.task_status_list).toCollection(taskList)
     //   view.spinner_task.setItems(taskList)
     //   view.spinner_task.setArrowColor(ContextCompat.getColor(activity?.applicationContext!!,R.color.colorPrimaryDark))

        v.fab_addTask.setOnClickListener {
            prepareAddTask(v)
        }
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        viewModel.init(activity?.applicationContext!!)
        viewModel.user_id.observe(viewLifecycleOwner, Observer {
            userId = it
        })

        observeProgressBar(v)
    }

    private fun prepareAddTask(view: View){
        val title = view.txt_title.text.toString()
        val body = view.txt_body.text.toString()
        val status = view.spinner_task.selectedItem.toString()
        val addTaskRequest = AddTaskRequest(userId,title, body, status)
        addTask(addTaskRequest)
    }

    private fun addTask(addTaskRequest: AddTaskRequest){
        viewModel.addTask(addTaskRequest).observe(viewLifecycleOwner, Observer {
            if(it.code()==201){
                val data=it.body()

                alert {
                    title = getString(R.string.title_success_dialog)
                    message=getString(R.string.msg_add_post_success)
                    isCancelable=false
                    positiveButton(getString(R.string.btn_ok)) { dialog ->
                        dialog.dismiss()
                    }
                }.show()
                Log.e(TAG,data?.title.toString())
            }
            else{
                val msg = "error code : ${it.code()} error message : ${it.errorBody()}"
                alert {
                    title = getString(R.string.title_error_dialog)
                    message=msg
                    isCancelable=false
                    positiveButton(getString(R.string.btn_ok)) { dialog ->
                        txt_title.text?.clear()
                        txt_body.text?.clear()
                        dialog.dismiss()
                    }
                }.show()
                Log.e(TAG,"error code : ${it.code()} error message : ${it.errorBody()}")
            }
        })
    }

    private fun observeProgressBar(view: View){
        viewModel.progress.observe(viewLifecycleOwner, Observer {
            if(it){
                view.progressBar.visibility = View.VISIBLE
            }
            else{
                view.progressBar.visibility = View.GONE
            }
        })
    }

}
