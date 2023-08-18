package com.example.avito_tech_bx_android_trainee_assigment.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.avito_tech_bx_android_trainee_assigment.R
import com.example.avito_tech_bx_android_trainee_assigment.fragments.contract.Navigator
import com.example.avito_tech_bx_android_trainee_assigment.fragments.contract.navigator
import viewmodel.ViewmodelRecyclerviewViewModel

class PickedItemFragment : Fragment() {
    private var param1: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("FRAGMENT_2", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        param1 = requireArguments().getInt(ARG_PARAM1, 0)
        Log.d("FRAGMENT_2", "onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picked_item, container, false).apply {
            findViewById<TextView>(R.id.tv_picked_item_number).text = param1.toString()
            findViewById<View>(R.id.iv_picked_item_cancel).setOnClickListener {
                Log.d(TAG, "onCreateView: parent = $parentFragment")
//                navigator().deleteItem(param1)
            }
        }
        Log.d("FRAGMENT_2", "onCreateView")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        // Вызовы, связанные с родительским фрагментом
//        val parentFragment = requireParentFragment()
//        val parentFragmentManager = parentFragment.parentFragmentManager

        // Ваши операции с фрагментом
        Log.d("FRAGMENT_2", "onViewCreated")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("FRAGMENT_2", "onViewStateRestored")
    }

    override fun onStart() {
        super.onStart()
        Log.d("FRAGMENT_2", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("FRAGMENT_2", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("FRAGMENT_2", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("FRAGMENT_2", "onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("FRAGMENT_2", "onSaveInstanceState")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("FRAGMENT_2", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FRAGMENT_2", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("FRAGMENT_2", "onDetach")
    }




    companion object {
        const val TAG = "PickedItemFragment"
        private const val ARG_PARAM1 = "${TAG}.extra.itemNumber"

        @JvmStatic
        fun newInstance(param1: Int) = PickedItemFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_PARAM1, param1)
            }
        }
    }
}