package com.example.avito_tech_bx_android_trainee_assigment.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.avito_tech_bx_android_trainee_assigment.R

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "itemNumber"

/**
 * A simple [Fragment] subclass.
 * Use the [PickedItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PickedItemFragment : Fragment() {
    private var param1: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1, 0)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picked_item, container, false).apply {
            findViewById<TextView>(R.id.tv_picked_item_number).text = param1.toString()
            findViewById<ImageView>(R.id.iv_picked_item_cancel).setOnClickListener {

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        // Вызовы, связанные с родительским фрагментом
//        val parentFragment = requireParentFragment()
//        val parentFragmentManager = parentFragment.parentFragmentManager

        // Ваши операции с фрагментом

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         */
        @JvmStatic
        fun newInstance(param1: Int)

        = PickedItemFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
                    }
                    val fragmentPickedItem = PickedItemFragment()
                    fragmentPickedItem.arguments = arguments
                    return@apply
                }
    }
}