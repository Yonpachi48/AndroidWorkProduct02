package app.takahashi.yonpachi.androidworkproduct02

import com.google.firebase.firestore.DocumentId
import java.util.*

data class Task(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val sentence: String = "",
)
