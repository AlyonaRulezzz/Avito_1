package com.example.avito_tech_bx_android_trainee_assigment.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.avito_tech_bx_android_trainee_assigment.R
import com.example.avito_tech_bx_android_trainee_assigment.databinding.ItemNumberLayoutBinding
import com.example.avito_tech_bx_android_trainee_assigment.fragments.PagerFragment
import com.example.avito_tech_bx_android_trainee_assigment.model.NumberModel
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_number_layout.view.*
import com.example.avito_tech_bx_android_trainee_assigment.fragments.PickedItemFragment


class NumberAdapter(
    val listener: ClickListener,
): RecyclerView.Adapter<NumberAdapter.NumberViewHolder>() {


    private var numberList = emptyList<NumberModel>()

    private var i = 0

    inner class NumberViewHolder(val binding: ItemNumberLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(value: NumberModel) {
            itemView.tv_number.text = value.number.toString()
            val res = ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete_24)
            itemView.iv_cancel.setImageDrawable(res)
            itemView.iv_cancel.setOnClickListener {
                listener.deleteItem(value.number)
//                notifyItemRemoved(layoutPosition)
//                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_number_layout, parent, false)
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNumberLayoutBinding.inflate(inflater, parent, false)
        return NumberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        val item = numberList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
//            listener.openItem(item.number)
            Log.d("MY_LOG_clickListener", "inside clickListenerOnNumber for PickedItemFragment")
            val fragmentPickedItem = PickedItemFragment.newInstance(item.number)
                    val activity = it.context as AppCompatActivity
//            val activity = it.context as PagerFragment
//            val fragmentManager = activity.childFragmentManager
            val fragmentManager = activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragmentPickedItem)
                .addToBackStack(null)
                .commit()

        }
    }

    override fun getItemCount(): Int {
        return numberList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<NumberModel>) {
        numberList = list
        notifyDataSetChanged()
    }

    interface ClickListener {

        fun openItem(value: Int)

        fun deleteItem(value: Int)
    }
}