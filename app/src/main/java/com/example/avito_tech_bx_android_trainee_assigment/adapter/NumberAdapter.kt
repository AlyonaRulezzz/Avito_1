package com.example.avito_tech_bx_android_trainee_assigment.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.avito_tech_bx_android_trainee_assigment.R
import com.example.avito_tech_bx_android_trainee_assigment.databinding.ItemNumberLayoutBinding
import com.example.avito_tech_bx_android_trainee_assigment.model.NumberModel
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_number_layout.view.*
import kotlin.properties.Delegates

class NumberAdapter(
    val listener: (value: Int) -> Unit,
): RecyclerView.Adapter<NumberAdapter.NumberViewHolder>() {


    private var numberList = emptyList<NumberModel>()

    private var i = 0

    inner class NumberViewHolder(val binding: ItemNumberLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(value: NumberModel) {
            itemView.tv_number.text = value.number.toString()
            itemView.tv_delete.text = value.x
            binding.ivCancel.setBackgroundResource(R.drawable.ic_launcher_background)
            itemView.tv_delete.setOnClickListener {
                listener(value.number)
//            holder.itemView.rv_number.removeViewAt(position)
                notifyItemRemoved(position)
//            notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        Log.d("MY_LOG", "onCreateViewHolder: ${++i}")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_number_layout, parent, false)
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNumberLayoutBinding.inflate(inflater, parent, false)
        return NumberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        val item = numberList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return numberList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<NumberModel>) {
        numberList = list
        notifyDataSetChanged()
    }

}