package com.example.avito_tech_bx_android_trainee_assigment

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.avito_tech_bx_android_trainee_assigment.adapter.NumberAdapter
import com.example.avito_tech_bx_android_trainee_assigment.databinding.ActivityMainBinding
import com.example.avito_tech_bx_android_trainee_assigment.model.NumberModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import viewmodel.MainActivityViewModel
import viewmodel.MainActivityViewModelFactory
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private val adapter: NumberAdapter = NumberAdapter(::adapterListener)

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, MainActivityViewModelFactory())[MainActivityViewModel::class.java]
    }

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }
    private val editor: SharedPreferences.Editor by lazy {
        sharedPreferences.edit()
    }

    val APP_PREFERENCES = "mySettings"
    val APP_PREFERENCES_LIST = "mySettingsList"
//    val sharedPreferences = getSharedPreferences("mySettings", Context.MODE_PRIVATE)
//    val editor: SharedPreferences.Editor = sharedPreferences.edit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.rvNumber.adapter = adapter
        initViewModel()
    }

    private fun initViewModel() {
//        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
//        editor = sharedPreferences.edit()

//        viewModel.liveDataList.value.apply { loadListFromSharedPreferences(this@MainActivity, "listOfNumberModel") }
        viewModel._liveDataList.value = loadListFromSharedPreferences(this, "listOfNumberModel")
        Log.d("MY_LOG_on_create", sharedPreferences.getString("listOfNumberModel", null).toString())

        viewModel.liveDataList.observe(this) {
            it?.let { adapter.setList(it) }
            binding.rvNumber.scrollToPosition(it.lastIndex)
        }
    }

    private fun adapterListener(value: Int) {
        Toast.makeText(this, "deleted $value", Toast.LENGTH_SHORT).show()
        viewModel.deleteItem(value)
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
            sharedPreferences.getString(key, null) // TODO: default заменить на лист из 15 элементов

        // Преобразуем строку JSON обратно в список
        val type = object : TypeToken<List<NumberModel>>() {}.type
        return Gson().fromJson(jsonList, type) as List<NumberModel> ?: emptyList<NumberModel>()  // TODO: default заменить на лист из 15 элементов
    }

}