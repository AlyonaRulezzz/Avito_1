package com.example.avito_tech_bx_android_trainee_assigment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.avito_tech_bx_android_trainee_assigment.adapter.NumberAdapter
import com.example.avito_tech_bx_android_trainee_assigment.databinding.ActivityMainBinding
import viewmodel.MainActivityViewModel
import viewmodel.MainActivityViewModelFactory

class MainActivity : AppCompatActivity() {

    private val adapter: NumberAdapter = NumberAdapter(::adapterListener)

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, MainActivityViewModelFactory())[MainActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.rvNumber.adapter = adapter
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.liveDataList.observe(this) {
            it?.let { adapter.setList(it) }
            binding.rvNumber.scrollToPosition(it.lastIndex)
        }
    }

    private fun adapterListener(value: Int) {
        Toast.makeText(this, "deleted $value", Toast.LENGTH_SHORT).show()
        viewModel.deleteItem(value)
    }
}