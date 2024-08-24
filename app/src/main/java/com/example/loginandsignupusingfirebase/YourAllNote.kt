package com.example.loginandsignupusingfirebase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginandsignupusingfirebase.databinding.ActivityYourAllNoteBinding
import com.example.loginandsignupusingfirebase.databinding.DailogUpdateNoteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class YourAllNote : AppCompatActivity(), noteAdapter.OnItemClickListener {
    private  val binding: ActivityYourAllNoteBinding by lazy {
        ActivityYourAllNoteBinding.inflate(layoutInflater)
    }
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        recyclerView = binding.noteRecyclerView
        recyclerView.layoutManager= LinearLayoutManager(this)
        // inisialize database
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val noteRef = database.child("users").child(user.uid).child("notes")
            noteRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val noteList = mutableListOf<NoteItem>()
                    for(noteSnapshot in snapshot.children){
                        val note = noteSnapshot.getValue(NoteItem::class.java)
                        note?.let {
                            noteList.add(it)
                        }

                    }
                    noteList.reverse()
                    val adapter = noteAdapter(noteList,this@YourAllNote)
                    recyclerView.adapter = adapter

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }



    }

    override fun onDeleteClick(noteId: String) {
        val currentUser = auth.currentUser
        currentUser?.let { user ->

            val noteRef = database.child("users").child(user.uid).child("notes")
            noteRef.child(noteId).removeValue()

        }}

        override fun onUpdateClick(noteId: String, newTitle: String, newDescription: String) {

            // for show dilog box

            val dailogBinding : DailogUpdateNoteBinding = DailogUpdateNoteBinding.inflate(LayoutInflater.from(this))
            val dailog = AlertDialog.Builder(this).setView(dailogBinding.root)
                .setTitle("Update Notes")
                .setPositiveButton("Update"){dialog,_ ->
                    val newTitle = dailogBinding.updatetitle.text.toString()
                    val newDescription = dailogBinding.updatediscreeption.text.toString()
                    updateNoteDatabase(noteId,newTitle,newDescription)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancle1"){dialog,_ ->
                    dialog.dismiss()



                }
                .create()
            dailogBinding.updatetitle.setText(newTitle)
            dailogBinding.updatediscreeption.setText(newDescription)
            dailog.show()





        }

    private fun updateNoteDatabase(noteId: String, newTitle: String, newDescription: String) {
        val currrentUser = auth.currentUser
        currrentUser?.let { user ->
            val noteRef = database.child("users").child(user.uid).child("notes")
            val updateNote = NoteItem(newTitle,newDescription,noteId)
            noteRef.child(noteId).setValue(updateNote)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        Toast.makeText(this, "Note Updated Successful ", Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this, "Failed To Update Note", Toast.LENGTH_SHORT).show()
                    }

                }

        }

    }

}