package com.example.loginandsignupusingfirebase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginandsignupusingfirebase.databinding.ActivityAddNoteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNote : AppCompatActivity() {
    private  val binding: ActivityAddNoteBinding by lazy {
        ActivityAddNoteBinding.inflate(layoutInflater)
    }

    private  lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // inisialize database
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        binding.save.setOnClickListener {

            // get text from edit text
            val title = binding.title.text.toString()
            val description = binding.description.text.toString()

            if (title.isEmpty()||description.isEmpty()){
                Toast.makeText(this, "Please Fill All The Details", Toast.LENGTH_SHORT).show()
            }else{
                val carenUser = auth.currentUser
                carenUser?.let {user->
                    // Generate unique key for note
                    val noteKey = database.child("users").child(user.uid).child("notes").push().key

                    // note item instance
                    val noteItem = NoteItem(title,description)
                    if (noteKey!=null)
                        // add notes to the user note
                        database.child("users").child(user.uid).child("notes").child(noteKey).setValue(noteItem)
                            .addOnCompleteListener{ task ->
                                if (task.isSuccessful){
                                    Toast.makeText(this, "Note Added Successfully", Toast.LENGTH_SHORT).show()
                                    finish()
                                }else{
                                    Toast.makeText(this, "Failed To Add Note", Toast.LENGTH_SHORT).show()
                                }

                            }


                }

            }
        }
    }
}