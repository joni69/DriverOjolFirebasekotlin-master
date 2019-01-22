package com.udacoding.DriverintraojolfirebaseKotlin

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.support.v4.content.ContextCompat
import android.util.Log.d
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.jetbrains.anko.toast

class TrackingService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onCreate(){
        super.onCreate()
        requestLocation()
    }
    var key: String? = null

    private fun requestLocation() {
//        get Koordinat terbaru
        var request = LocationRequest()
//        update location per detik
        request.interval = 1000

//        setting akurasi
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        var client = LocationServices.getFusedLocationProviderClient(this@TrackingService)
        var permission = ContextCompat.checkSelfPermission(this@TrackingService,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (permission == PackageManager.PERMISSION_GRANTED) {
            client.requestLocationUpdates(request, object : LocationCallback() {


                //            location update terbaru
                override fun onLocationResult(p0: LocationResult?) {
                    super.onLocationResult(p0)

                    d("service12",p0.toString())

                    val lat = p0?.lastLocation?.latitude
                    val lot = p0?.lastLocation?.longitude
//                    toast("lat")
                    inserts(lat,lot)

                    d("koordinat","$lat,$lot")
                }
            }, null)
        }
    }

    private fun inserts(lat:Double?,lot:Double?) {
//    toast("asdasdasdasdasdas")
        val uidr = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseDatabase.getInstance()
        val rf = db.getReference("Driver")
        val qwr = rf.orderByChild("uid").equalTo(uidr)
        qwr.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {

                for (issue in p0.children) {
                    key = issue.key
                    rf.child(key ?:"").child("lat").setValue(lat)
                    rf.child(key ?: "").child("lon").setValue(lot)
                }

            }
        })
    }
}