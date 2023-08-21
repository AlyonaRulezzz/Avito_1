package com.example.avito_tech_bx_android_trainee_assigment.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.avito_tech_bx_android_trainee_assigment.R
import com.example.avito_tech_bx_android_trainee_assigment.adapters.PagerAdapter
import com.example.avito_tech_bx_android_trainee_assigment.databinding.FragmentPagerBinding

class PagerFragment : Fragment() {

    lateinit var binding: FragmentPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_pager, container, false)

        binding = FragmentPagerBinding.bind(rootView)

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(ViewmodelRecyclerviewFragment())
        fragmentList.add(PickedItemFragment())

        val pager: ViewPager2 = binding.pager
        val adapter = PagerAdapter(requireActivity().supportFragmentManager, lifecycle, fragmentList)
        pager.adapter = adapter

        return rootView
    }


}