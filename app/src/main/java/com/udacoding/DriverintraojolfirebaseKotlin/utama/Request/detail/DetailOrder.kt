package com.udacoding.DriverintraojolfirebaseKotlin.utama.Request.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.udacoding.DriverintraojolfirebaseKotlin.R
import com.udacoding.DriverintraojolfirebaseKotlin.utama.HomeActivity
import com.udacoding.DriverintraojolfirebaseKotlin.utama.Request.HandleFragment
import com.udacoding.DriverintraojolfirebaseKotlin.utama.home.model.Booking
import com.udacoding.DriverintraojolfirebaseKotlin.utama.home.model.Bounds
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.lang.StringBuilder

class DetailOrder : AppCompatActivity(), OnMapReadyCallback {


    var booking: Booking? = null
    var mMap: GoogleMap? = null
    var status: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)


        status = intent.getIntExtra("status",1)
        booking = intent.getSerializableExtra("booking") as? Booking



        if (status == 2) {
            homebuttonnext.text = "Complete Order"
        }

        homeAwal.text = booking?.lokasiAwal
        homeTujuan.text = booking?.lokasiTujuan
        homeprice.text = booking?.harga
        homeWaktudistance.text = booking?.jarak



        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0
//        create marker
        val lokasiawal = LatLng(booking?.latAwal ?: 0.0, booking?.lonAwal ?: 0.0)
        val lokasitujuan = LatLng(booking?.latTujuan ?: 0.0, booking?.lonTujuan ?: 0.0)

        mMap?.addMarker(
            MarkerOptions().position(lokasiawal)
                .title("awal").snippet(booking?.lokasiAwal)
        )
        mMap?.addMarker(
            MarkerOptions().position(lokasitujuan)
                .title("tujuan").snippet(booking?.lokasiTujuan)
        )

//        bound

        val builder = LatLngBounds.builder()
        builder.include(lokasiawal)
        builder.include(lokasitujuan)

        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val padding = (width * 0.12).toInt()

        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasiawal, 14f))
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasitujuan, 14f))
        mMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), width,height,padding))

//        fungsi update /take driver
        var key : String? = null
        val db = FirebaseDatabase.getInstance()
        val reference = db.getReference("Order")
        val query = reference.orderByChild("tanggal").equalTo(booking?.tanggal)
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {


            }

            override fun onDataChange(p0: DataSnapshot) {
                for (issue in p0.children){
                    key = issue.key
                }
            }
        })

        homebuttonnext.onClick {
          if (status == 1) {
              reference.child(key?:"").child("status").setValue(2)
              reference.child(key?:"").child("driver").setValue(FirebaseAuth.getInstance().currentUser?.uid)
              toast("menerima pesanan")
              startActivity<HomeActivity>("ff" to 1)
          }else{
              reference.child(key?:"").child("status").setValue(4)
              startActivity<HomeActivity>("ff" to 2)
          }

        }
    }
}
