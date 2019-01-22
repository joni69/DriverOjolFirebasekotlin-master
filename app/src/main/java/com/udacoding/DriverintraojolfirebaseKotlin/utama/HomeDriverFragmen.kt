package com.udacoding.DriverintraojolfirebaseKotlin.utama


import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.udacoding.DriverintraojolfirebaseKotlin.R
import com.udacoding.DriverintraojolfirebaseKotlin.utama.Request.ComplateFragment
import com.udacoding.DriverintraojolfirebaseKotlin.utama.Request.HandleFragment
import com.udacoding.DriverintraojolfirebaseKotlin.utama.Request.RequestFragment
import com.udacoding.DriverintraojolfirebaseKotlin.utama.home.HomeFragment
import com.udacoding.DriverintraojolfirebaseKotlin.utama.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.driver_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeDriverFragmen : Fragment() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.itemRequest -> {

                setFragment(RequestFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemHendle -> {

                setFragment(HandleFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemComplate -> {

                setFragment(ComplateFragment())
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.driver_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragment(RequestFragment())
        navigation2.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    fun setFragment(fragment : Fragment){

        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container,fragment)?.commit()
    }
}
