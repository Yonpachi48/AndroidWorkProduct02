package app.takahashi.yonpachi.androidworkproduct02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import app.takahashi.yonpachi.androidworkproduct02.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
        }

        val db = Firebase.firestore

        // RecyclerViewの設定
        val taskAdapter = TaskAdapter()
        binding.recyclerView.adapter = taskAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // cellのクリックイベント
        taskAdapter.setOnClickListener(
            object: TaskAdapter.OnItemClickListener {
                override fun onClick(view: View, task: Task) {
                    val context = view.context
                    val toDetailActivityIntent = Intent(context, DetailActivity::class.java)
                    toDetailActivityIntent.putExtra("tasks", task.id)
                    context.startActivity(toDetailActivityIntent)
                }
            }
        )

        // アプリ起動時に、保存されているデータを取得する
        db.collection("tasks")
            .get()
            .addOnSuccessListener { tasks ->
                val taskList = ArrayList<Task>()
                tasks.forEach { taskList.add(it.toObject(Task::class.java)) }
                taskAdapter.submitList(taskList)
            }
            .addOnFailureListener { exception ->
                Log.d(READ_TAG, "Error getting documents: ", exception)
            }

        binding.addButton.setOnClickListener {
            val toAddActivityIntent = Intent(this, AddActivity::class.java)
            startActivity(toAddActivityIntent)
        }

        // データの変更をリアルタイムでアプリに反映する
        db.collection("tasks").addSnapshotListener { tasks, e ->
            if(e != null) {
                Log.w(READ_TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (tasks != null) {
                val taskList = ArrayList<Task>()
                tasks.forEach { taskList.add(it.toObject(Task::class.java)) }
                taskAdapter.submitList(taskList)
            } else {
                Log.d(READ_TAG, "Current data: null")
            }
        }
    }

    companion object {
        const val READ_TAG = "errorGetting"
    }
}