package app.takahashi.yonpachi.androidworkproduct02

import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.takahashi.yonpachi.androidworkproduct02.databinding.TaskListItemBinding

class TaskAdapter : ListAdapter<Task, TaskViewHolder>(diffUtilCallback){

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        setOnClickListener(listener)
        val view = TaskListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            listener.onClick(it, getItem(position))
        }
    }

    interface OnItemClickListener {
        fun onClick(view: View, task: Task)
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}

class TaskViewHolder (
    private val binding: TaskListItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(task: Task) {
        binding.titleTextView.text = task.title
        binding.sentenceTextView.text = task.sentence
    }
}

private val diffUtilCallback = object : DiffUtil.ItemCallback<Task>() {
    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }
}
