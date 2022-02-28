package app.takahashi.yonpachi.androidworkproduct02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import app.takahashi.yonpachi.androidworkproduct02.databinding.ActivityDetailBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
        }
        val taskId = intent.getStringExtra("tasks")

        val db = Firebase.firestore
        val docRef = db.collection("tasks").document(taskId.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    binding.detailTitleText.text = document.data?.get("title").toString()
                    binding.detailSentenceText.text = document.data?.get("sentence").toString()
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }



        binding.editButton.setOnClickListener{
            val toChangeActivityIntent = Intent(this, ChangeActivity::class.java)
            toChangeActivityIntent.putExtra("tasks", taskId)
            startActivity(toChangeActivityIntent)
        }

        binding.deleteButton.setOnClickListener {
            db.collection("tasks").document(taskId.toString())
                .delete()
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

            finish()
        }

    }

    companion object {
        const val TAG = "TaskTag"
    }
}