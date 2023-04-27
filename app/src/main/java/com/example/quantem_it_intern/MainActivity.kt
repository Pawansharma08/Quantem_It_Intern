package com.example.quantem_it_intern

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.quantem_it_intern.Adapter.LoginAdapter
import com.example.quantem_it_intern.Fragment.LoginFrag
import com.example.quantem_it_intern.Fragment.SignupFrag
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val fragmentTitles = arrayOf("Login", "Signup")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide();


        
        // Tabs for Login and SignUp
        val tabLayout = findViewById<TabLayout>(R.id.tab_btn)
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val loginAdapter = LoginAdapter(this)
        val bundle = Bundle()
//        bundle.putSerializable("USER", user)

        val loginFragment: Fragment = LoginFrag()
        loginFragment.arguments = bundle

        loginAdapter.addFragment(loginFragment)
        loginAdapter.addFragment(SignupFrag())
        viewPager.adapter = loginAdapter

        TabLayoutMediator(
            tabLayout, viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = fragmentTitles[position]
        }.attach()
    }
}