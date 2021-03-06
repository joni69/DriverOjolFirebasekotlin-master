package com.udacoding.DriverintraojolfirebaseKotlin.utama.Request


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.udacoding.DriverintraojolfirebaseKotlin.R
import com.udacoding.DriverintraojolfirebaseKotlin.utama.Request.detail.DetailOrder
import com.udacoding.DriverintraojolfirebaseKotlin.utama.history.adapter.Historydapter
import com.udacoding.DriverintraojolfirebaseKotlin.utama.home.model.Booking
import kotlinx.android.synthetic.main.fragment_history.*
import org.jetbrains.anko.support.v4.startActivity
import java.lang.IllegalStateException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ComplateFragment : Fragment() {

    var auth: FirebaseAuth? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val uid = auth?.currentUser?.uid
        val db = FirebaseDatabase.getInstance()
        val reference = db.getReference("Order")
        val query = reference.orderByChild("uid").equalTo(uid)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var data = ArrayList<Booking>()
                for (issue in p0.children) {

                    val booking = issue.getValue(Booking::class.java)
                    if (booking?.status == 4) {
                        data.add(booking ?: Booking())
                        showData(data)
                    }
                }
            }
        })
    }

    private fun showData(data: ArrayList<Booking>) {
        try {
            recyclerview.adapter = Historydapter(data,object : Historydapter.onClickItem{
                override fun Clic(Item: Booking) {
                    startActivity<DetailOrder>("booking" to Item,"status" to 3)
                }
            })
            recyclerview.layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?

        } catch (e: IllegalStateException){

        }
    }


}
