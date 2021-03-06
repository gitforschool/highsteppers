package com.hfad.firebaselogin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.hfad.firebaselogin.FireWalkAdapter
import com.hfad.firebaselogin.R
import com.hfad.firebaselogin.models.Walk
import com.hfad.firebaselogin.utils.Constants

class DisplayWalksActivity : AppCompatActivity() {
    val a= FirebaseAuth.getInstance().currentUser!!.uid
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionReference: CollectionReference =db.collection(Constants.USERS)

    var fAdapter: FireWalkAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_walks)
    }
//    fun setUpRecyclerView(){
//
//        val query: Query = db.collection(Constants.USERS)
//        val firestoreRecyclerOptions: FirestoreRecyclerOptions<Walk> =
//                FirestoreRecyclerOptions.Builder<Walk>()
//                .set(query, Walk::class.java)
//                .build()
//
//        fAdapter = FireWalkAdapter(firestoreRecyclerOptions)
//
//
//
//    }
}