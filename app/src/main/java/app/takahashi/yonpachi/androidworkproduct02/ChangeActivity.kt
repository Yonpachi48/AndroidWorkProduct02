package app.takahashi.yonpachi.androidworkproduct02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import app.takahashi.yonpachi.androidworkproduct02.databinding.ActivityChangeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChangeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
        }

        val taskId = intent.getStringExtra("tasks")

        val db = Firebase.firestore
        val docRef = db.collection("tasks").document(taskId.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(DetailActivity.TAG, "DocumentSnapshot data: ${document.data}")
                    binding.changeTitleEditText.hint = document.data?.get("title").toString()
                    binding.changeSentenceEditText.hint = document.data?.get("sentence").toString()
                } else {
                    Log.d(DetailActivity.TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(DetailActivity.TAG, "get failed with ", exception)
            }

        binding.changeCheckButton.setOnClickListener {
            // データの上書き
            val task = Task (
                title = binding.changeTitleEditText.editText?.text.toString(),
                sentence = binding.changeSentenceEditText.editText?.text.toString()
            )
            db.collection("tasks").document(taskId.toString())
                .set(task)
                .addOnSuccessListener { Log.d("setTag", "Document successfully written!") }
                .addOnFailureListener{ e-> Log.w("setTag", "Error written document", e)}

            // 画面遷移とActivityの終了
            val toMainActivityIntent = Intent(this, MainActivity::class.java)
            toMainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(toMainActivityIntent)
        }
    }
}