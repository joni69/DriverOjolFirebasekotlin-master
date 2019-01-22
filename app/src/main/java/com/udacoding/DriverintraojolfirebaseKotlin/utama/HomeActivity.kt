package com.udacoding.DriverintraojolfirebaseKotlin.utama

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.udacoding.DriverintraojolfirebaseKotlin.R
import com.udacoding.DriverintraojolfirebaseKotlin.TrackingService
import com.udacoding.DriverintraojolfirebaseKotlin.utama.profile.ProfileFragment
import com.udacoding.DriverintraojolfirebaseKotlin.utama.history.HistoryFragment
import com.udacoding.DriverintraojolfirebaseKotlin.utama.home.HomeFragment
//import com.udacoding.DriverintraojolfirebaseKotlin.utama.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startService
import org.jetbrains.anko.toast

class HomeActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                setFragment(HomeDriverFragmen())
                return@OnNavigationItemSelectedListener true
            }
//            R.id.navigation_dashboard -> {
//
//                setFragment(HistoryFragment())
//                return@OnNavigationItemSelectedListener true
//            }
            R.id.navigation_profile -> {

                setFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val data = intent.getIntExtra("ff",1)
//        toast("asaaa")

        checkPermissions()
        setFragment(HomeDriverFragmen())

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION),1)
        }else {
            startService(Intent(this,TrackingService::class.java))
        }
    }

    fun setFragment(fragment : Fragment){

        supportFragmentManager.beginTransaction().replace(R.id.container2,fragment).commit()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            startService(Intent(this,TrackingService::class.java))
        }
    }
}
