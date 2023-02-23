package com.example.avito_tech_bx_android_trainee_assigment.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.avito_tech_bx_android_trainee_assigment.R
import com.example.avito_tech_bx_android_trainee_assigment.model.NumberModel
import kotlinx.android.synthetic.main.item_number_layout.view.*

class NumberAdapter: RecyclerView.Adapter<NumberAdapter.NumberViewHolder>() {

    private var numberList = emptyList<NumberModel>()

    class NumberViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_number_layout, parent, false)
        return NumberViewHolder(view)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        holder.itemView.tv_number.text = numberList[position].number.toString()
        holder.itemView.tv_delete.text = numberList[position].x
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