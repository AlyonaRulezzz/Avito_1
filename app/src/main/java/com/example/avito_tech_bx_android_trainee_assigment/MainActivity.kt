package com.example.avito_tech_bx_android_trainee_assigment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.avito_tech_bx_android_trainee_assigment.adapter.NumberAdapter
import com.example.avito_tech_bx_android_trainee_assigment.databinding.ActivityMainBinding
import com.example.avito_tech_bx_android_trainee_assigment.model.NumberModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.view.*
import viewmodel.MainActivityViewModel
import viewmodel.MainActivityViewModelFactory

class MainActivity : AppCompatActivity() {

    private val adapter: NumberAdapter = NumberAdapter(::adapterListener)

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, MainActivityViewModelFactory(loadListFromSharedPreferences(this, "listOfNumberModel")))[MainActivityViewModel::class.java]
    }

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }
    private val editor: SharedPreferences.Editor by lazy {
        sharedPreferences.edit()
    }

//    private lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.rvNumber.adapter = adapter
        binding.rvNumber.layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            GridLayoutManager(this, 2)
        } else {
            GridLayoutManager(this, 4)
        }
//        binding.rvNumber.layoutManager = layoutManager
        initViewModel()
    }


    private fun initViewModel() {
        Log.d("MY_LOG_on_create", sharedPreferences.getString("listOfNumberModel", null).toString())

        viewModel._liveDataList.observe(this) {
            it?.let { adapter.setList(it) }
            binding.rvNumber.scrollToPosition(it.lastIndex)
        }
    }

    private fun adapterListener(value: Int) {
        AlertDialog.Builder(this)
            .setTitle("Delete item")
            .setMessage("Babe, are you sure?")
            .setIcon(R.drawable.ic_baseline_delete_24)
            .setPositiveButton("Yeap") { _, _ ->
                Toast.makeText(this, "deleted $value", Toast.LENGTH_SHORT).show()
                viewModel.deleteItem(value)
            }
            .setNegativeButton("Nope") {_, _ ->
                Toast.makeText(this, "Good choice, pal)", Toast.LENGTH_SHORT).show()
            }
            .create()
            .show()
    }


    override fun onStop() {
        saveListToSharedPreferences(this, viewModel.liveDataList.value!!, "listOfNumberModel")
        Log.d("MY_LOG_on_stop", sharedPreferences.getString("listOfNumberModel", null).toString())
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("MY_LOG_on_destroy", sharedPreferences.getString("listOfNumberModel", null).toString())
//        saveListToSharedPreferences(this, viewModel.liveDataList.value!!, "listOfNumberModel")
        super.onDestroy()
    }

    // Функция для сохранения списка в SharedPreferences
    fun saveListToSharedPreferences(context: Context, list: List<NumberModel>, key: String) {

        //  Преобразуем список в строку формата JSON
        val gson = Gson()
        val jsonList = gson.toJson(list)

        //  Сохраняем строку в SharedPreferences
        editor.putString(key, jsonList)
        editor.apply()
    }
//
    // Функция для загрузки списка из SharedPreferences
    private fun loadListFromSharedPreferences(context: Context, key: String): List<NumberModel> {

        // Получаем строку из SharedPreferences
        val jsonList =
            sharedPreferences.getString(key, null)

        // Преобразуем строку JSON обратно в список
        val type = object : TypeToken<List<NumberModel>>() {}.type
        return (Gson().fromJson(jsonList, type) as? List<NumberModel>) ?: emptyList<NumberModel>()

}

}