package com.example.avito_tech_bx_android_trainee_assigment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.avito_tech_bx_android_trainee_assigment.databinding.ActivityMainBinding
import com.example.avito_tech_bx_android_trainee_assigment.fragments.ViewmodelRecyclerviewFragment

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_main)
        setContentView(binding.root)
        gotToFragment(ViewmodelRecyclerviewFragment())
    }

    private fun gotToFragment(fragment: Fragment) {
        fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
    }


}