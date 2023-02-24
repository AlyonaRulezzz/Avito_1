package com.example.avito_tech_bx_android_trainee_assigment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.avito_tech_bx_android_trainee_assigment.adapter.NumberAdapter
import com.example.avito_tech_bx_android_trainee_assigment.databinding.ActivityMainBinding
import com.example.avito_tech_bx_android_trainee_assigment.model.NumberModel
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: NumberAdapter
    lateinit var recyclerView: RecyclerView
    var list = myNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initial()
    }

    private fun initial() {
        recyclerView = binding.rvNumber
        adapter = NumberAdapter(::adapterListener)
        recyclerView.adapter = adapter

        adapter.setList(list)
//        deleteItem(14)
    }

    private fun adapterListener(value: Int) {
        Toast.makeText(this, "deleted $value", Toast.LENGTH_LONG).show()
        adapter.setList(list.filter { it.number != value })
    }

    private fun deleteItem(i: Int) {
        GlobalScope.launch {
            delay(2_000)
            withContext(Dispatchers.Main) {
                adapter.setList(List(i) {NumberModel(it + 1, "x")})
            }
            deleteItem(i - 1)
        }
    }

    private fun myNumber(): ArrayList<NumberModel>{
        val numberList = ArrayList<NumberModel>()

        lateinit var number: NumberModel

        for (i in 1..15) {
            number = NumberModel(i, "x")
            numberList.add(number)
        }



        return numberList
    }

}