package com.example.avito_tech_bx_android_trainee_assigment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.avito_tech_bx_android_trainee_assigment.adapter.NumberAdapter
import com.example.avito_tech_bx_android_trainee_assigment.databinding.ActivityMainBinding
import com.example.avito_tech_bx_android_trainee_assigment.model.NumberModel
import kotlinx.coroutines.*
import java.util.LinkedList
import java.util.Queue

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: NumberAdapter
    lateinit var recyclerView: RecyclerView
    private var list = myNumber()
    var deletedPool: Queue<Int> = LinkedList()

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
        addItem()
    }

    private fun adapterListener(value: Int) {
        Toast.makeText(this, "deleted $value", Toast.LENGTH_SHORT).show()
        list = list.filter { it.number != value } as ArrayList<NumberModel>  ///////
        adapter.setList(list)
        deletedPool.add(value)
    }

    private fun deleteItem(i: Int) {
        GlobalScope.launch {
            delay(2_000)
            withContext(Dispatchers.Main) {
                adapter.setList(List(i) { NumberModel(it + 1) })
            }
            deleteItem(i - 1)
        }
    }

    private fun addItem() {

        lateinit var number: NumberModel

        var i = 16
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
            CoroutineScope(Dispatchers.Main).launch {
                while (true) {
                    if (deletedPool.isEmpty()) {
                        number = NumberModel(i)
                        list.add(number)
                        i++
                    } else {
                        list.add(NumberModel(deletedPool.remove()))
                    }
                    adapter.notifyDataSetChanged()
                    recyclerView.smoothScrollToPosition(adapter.itemCount)
                    delay(2_000)
                }
        }
            }
        }
    }

    private fun myNumber(): ArrayList<NumberModel>{
        val numberList = ArrayList<NumberModel>()

        lateinit var number: NumberModel
        for (i in 1..15) {
            number = NumberModel(i)
            numberList.add(number)
        }

        return numberList
    }

}