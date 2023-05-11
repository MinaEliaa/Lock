package com.example.lock.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lock.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView=findViewById(R.id.bottom_nav_bar)
        bottomNavigationView.setOnItemSelectedListener (NavigationBarView.OnItemSelectedListener {
                menuitem->

            if (menuitem.itemId  == R.id.navigation_encrypt){
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_contanier, EncryptFragment())
                    .commit()
            }
//            else if(menuitem.itemId == R.id.navigation_home){
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.fragment_contanier,HomeFragment())
//                    .commit()
//
//            }
            else if (menuitem.itemId == R.id.navigation_decrypt){
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_contanier, DecryptFragment())
                    .commit()
            }
            return@OnItemSelectedListener true
        })
        bottomNavigationView.selectedItemId=R.id.navigation_encrypt

    }
}