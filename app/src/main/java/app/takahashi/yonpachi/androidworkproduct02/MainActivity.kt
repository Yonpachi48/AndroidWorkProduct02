package app.takahashi.yonpachi.androidworkproduct02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        binding.addButton.setOnClickListener {
            val toAddActivityIntent = Intent(this, AddActivity::class.java)
            startActivity(toAddActivityIntent)
        }
    }
}