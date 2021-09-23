package com.yuchen.firebasephaseone

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.yuchen.firebasephaseone.data.Article
import com.yuchen.firebasephaseone.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.submitBtn.setOnClickListener {
            submitArticle()
        }

//        CoroutineScope(Dispatchers.IO).launch {
            setObserverData()
//        }
    }


    private fun setObserverData() {

        val articlesDb = db.collection("Documents")
        articlesDb.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("Firebase", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                snapshot.documentChanges.forEach {
                    if (it.document.get("created_time") != null) {
                        Log.i("Firebase", "data id: ${it.document.id}")
                        Log.i("Firebase", "id: ${it.document.get("id")}")
                        Log.i("Firebase", "title: ${it.document.get("title")}")
                        Log.i("Firebase", "content: ${it.document.get("content")}")
                        Log.i("Firebase", "tag: ${it.document.get("tag")}")
                        Log.i("Firebase", "author id: ${it.document.get("author_id")}")

                        val timestamp =
                            it.document.get("created_time") as? com.google.firebase.Timestamp
                        val milliseconds =
                            (timestamp?.seconds ?: 0) * 1000 + (timestamp?.nanoseconds
                                ?: 0) / 1000000
                        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        val netDate = Date(milliseconds)
                        Log.i("Firebase", "created time: ${sdf.format(netDate)}")
                        Log.i("Firebase", "--------------------------------------------------")
                    }
                }
            } else {
                Log.d("Firebase", "Current data: null")
            }
        }
    }

    private fun submitArticle() {

        val doc = db.collection("Documents").document()
        val article = Article(
            doc.id,
            "${binding.titleEditText.text}",
            "${binding.contentEditText.text}",
            when (binding.tagRadioGroup.checkedRadioButtonId) {
                R.id.radio_beauty -> getString(R.string.radio_beauty)
                R.id.radio_gossiping -> getString(R.string.radio_gossiping)
                R.id.radio_schoolLife -> getString(R.string.radio_schoolLife)
                else -> ""
            },
            "yu_chen",
            FieldValue.serverTimestamp()

        )
        doc.set(article)
            .addOnSuccessListener {
                binding.apply {
                    titleEditText.text = null
                    contentEditText.text = null
                    tagRadioGroup.clearCheck()
                }
                Toast.makeText(this, "新增成功", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "新增失敗", Toast.LENGTH_SHORT).show()
            }
    }
}
