package com.example.avito_tech_bx_android_trainee_assigment

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.avito_tech_bx_android_trainee_assigment.adapter.NumberAdapter
import com.example.avito_tech_bx_android_trainee_assigment.databinding.ActivityMainBinding
import com.example.avito_tech_bx_android_trainee_assigment.model.NumberModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    val APP_PREFERENCES = "mySettings"
    val APP_PREFERENCES_LIST = "mySettingsList"
    val sharedPreferences = getSharedPreferences("mySettings", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()

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

    override fun onStop() {
        super.onStop()

    }

    // Функция для сохранения списка в SharedPreferences
    fun SaveListToSharedPreferences(context: Context, list: List<NumberModel>, key: String) {

        //  Преобразуем список в строку формата JSON
        val gson = Gson()
        val jsonList = gson.toJson(list)

        //  Созраняем строку в SharedPreferences
        editor.putString(key, jsonList)
        editor.apply()
    }

    // Функция для загрузки списка из SharedPreferences
    fun loadListFromSharedPreferences(context: Context, key: String): List<Any> {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Получаем строку из SharedPreferences
        val jsonList = sharedPreferences.getString(key, null) // TODO: заменить на лист из 15 элементов

        // Преобразуем строку JSON обратно в список
        val type = object : TypeToken<List<Any>>() {}.type
        return Gson().fromJson(jsonList, type) ?: emptyList()


}