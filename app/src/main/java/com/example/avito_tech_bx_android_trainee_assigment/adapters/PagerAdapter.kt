package com.example.avito_tech_bx_android_trainee_assigment.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.avito_tech_bx_android_trainee_assigment.fragments.PickedItemFragment
import com.example.avito_tech_bx_android_trainee_assigment.fragments.ViewmodelRecyclerviewFragment

class PagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {

    private var fragmentList: ArrayList<Fragment> = arrayListOf(ViewmodelRecyclerviewFragment(), PickedItemFragment.newInstance(0))

    constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle, fragmentList: ArrayList<Fragment>) : this(fragmentManager, lifecycle) {
        this.fragmentList = fragmentList
    }

    override fun getItemCount(): Int {
//        return fragmentList.size
        return 2
    }

    override fun createFragment(position: Int): Fragment {
//        return fragmentList[position]
        return if (position == 0) ViewmodelRecyclerviewFragment() else PickedItemFragment.newInstance(0)
    }
}







//class PagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
//
//    lateinit var fragmentList: List<Fragment>
//
//    constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle, fragmentList: List<Fragment>) : this(fragmentManager, lifecycle) {
//        this.fragmentList = fragmentList
//    }
//
//    override fun getItemCount(): Int {
//        return fragmentList.size
//    }
//
//    override fun createFragment(position: Int): Fragment {
//        return if ((fragmentList.size == 2 ) and (position == 1)) {
//            fragmentList.get(1)
//        } else {
//            fragmentList.get(0)
//        }
//    }
//}