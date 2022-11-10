package com.droomptech.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.droomptech.chattingapp.activity.NumberActivity
import com.droomptech.chattingapp.adapter.ViewPagerAdapter
import com.droomptech.chattingapp.databinding.ActivityMainBinding
import com.droomptech.chattingapp.ui.CallFragment
import com.droomptech.chattingapp.ui.ChatFragment
import com.droomptech.chattingapp.ui.StatusFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val fragmentArrayList = ArrayList<Fragment>()

        fragmentArrayList.add(ChatFragment())
        fragmentArrayList.add(CallFragment())
        fragmentArrayList.add(StatusFragment())

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null){
            startActivity(Intent(this, NumberActivity::class.java))
            finish()
        }


        val adapter = ViewPagerAdapter(this, supportFragmentManager, fragmentArrayList)

        binding!!.viewPager.adapter = adapter

        binding!!.tabs.setupWithViewPager(binding!!.viewPager)

        println("This is Contained in my second commit")
        println("Experimental")
        println("Hello From Friend")

    }
}