package app.takahashi.yonpachi.androidworkproduct02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import app.takahashi.yonpachi.androidworkproduct02.databinding.ActivityAddBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
        }

        // FireStoreをインスタンス化
        val db = Firebase.firestore

        binding.checkButton.setOnClickListener {

            // Taskクラスをインスタンス化
            val task = Task(
                title = binding.titleEditText.editText?.text.toString(),
                sentence = binding.sentenceEditText.editText?.text.toString()
            )

            // TextEditの内容をdbに保存
            db.collection("tasks")
                .add(task)
                .addOnSuccessListener { documentRefernce ->
                    Log.d(ADD_TAG, "DcumentSnapshot added with ID: ${documentRefernce.id}")
                }
                .addOnFailureListener { e ->
                    Log.d(ADD_TAG, "Error adding document", e)
                }

            finish()
        }
    }



    companion object {
        const val ADD_TAG = "taskTag"
    }
}