package com.example.avito_tech_bx_android_trainee_assigment.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.avito_tech_bx_android_trainee_assigment.R
import com.example.avito_tech_bx_android_trainee_assigment.adapters.NumberAdapter
import com.example.avito_tech_bx_android_trainee_assigment.databinding.FragmentViewmodelRecyclerviewBinding
import com.example.avito_tech_bx_android_trainee_assigment.fragments.contract.Navigator
import com.example.avito_tech_bx_android_trainee_assigment.model.NumberModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import viewmodel.ViewmodelRecyclerviewViewModel
import viewmodel.ViewmodelRecyclerviewViewModelFactory


class ViewmodelRecyclerviewFragment : Fragment(), Navigator {
    private val adapter: NumberAdapter = NumberAdapter(
        object : NumberAdapter.ClickListener {
            override fun openItem(value: Int) {
//                childFragmentManager.beginTransaction()
//                    .replace(
//                        R.id.fragment_container,
//                        PickedItemFragment.newInstance(value),
//                        PickedItemFragment.TAG
//                    )
//                    .addToBackStack(null)
//                    .commit()
            }

            override fun deleteItem(value: Int) {
                adapterListener(value)
            }

        }
    )

    lateinit var binding: FragmentViewmodelRecyclerviewBinding

    private lateinit var factory: ViewmodelRecyclerviewViewModelFactory

    private lateinit var viewModel: ViewmodelRecyclerviewViewModel

    private val sharedPreferences: SharedPreferences by lazy {
        requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }
    private val editor: SharedPreferences.Editor by lazy {
        sharedPreferences.edit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("FRAGMENT_1", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FRAGMENT_1", "onCreate")
//        setRetainInstance(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        factory = ViewmodelRecyclerviewViewModelFactory(loadListFromSharedPreferences(requireContext(), "listOfNumberModel"))
        viewModel = ViewModelProvider(this, factory).get(ViewmodelRecyclerviewViewModel::class.java)

        // Сначала раздуть макет и получить переменную binding
        val rootView = inflater.inflate(R.layout.fragment_viewmodel_recyclerview, container, false)

        // После получения binding, установите адаптер для RecyclerView
//        binding = FragmentViewmodelRecyclerviewBinding.inflate(layoutInflater)
        binding = FragmentViewmodelRecyclerviewBinding.bind(rootView)
        binding.rvNumber.adapter = adapter
        binding.rvNumber.layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            GridLayoutManager(context, 2)
        } else {
            GridLayoutManager(context, 4)
        }
        initViewModel()
        Log.d("FRAGMENT_1", "onCreateView")

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("FRAGMENT_1", "onViewCreated")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("FRAGMENT_1", "onViewStateRestored")
    }

    override fun onStart() {
        super.onStart()
        Log.d("FRAGMENT_1", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("FRAGMENT_1", "onResume")
    }

    private fun initViewModel() {
        Log.d("MY_LOG_on_create", sharedPreferences.getString("listOfNumberModel", null).toString())

        viewModel._liveDataList.observe(viewLifecycleOwner) {
            it?.let {
                adapter.setList(it) }
            binding.rvNumber.scrollToPosition(it.lastIndex)
        }
    }

    private fun adapterListener(value: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete item")
            .setMessage("Babe, are you sure?")
            .setIcon(R.drawable.ic_baseline_delete_24)
            .setPositiveButton("Yeap") { _, _ ->
                Toast.makeText(context, "deleted $value", Toast.LENGTH_SHORT).show()
                viewModel.deleteItem(value)
                viewModel._liveDataList.observe(viewLifecycleOwner) {
                    it?.let {
                        adapter.setList(it) }
//                    binding.rvNumber.scrollToPosition(it.lastIndex)
                }
            }
            .setNegativeButton("Nope") {_, _ ->
                Toast.makeText(context, "Good choice, pal)", Toast.LENGTH_SHORT).show()
            }
            .create()
            .show()
    }


    override fun onPause() {
        super.onPause()
        saveListToSharedPreferences(requireContext(), viewModel.liveDataList.value!!, "listOfNumberModel")
        Log.d("MY_LOG_on_pause", sharedPreferences.getString("listOfNumberModel", null).toString())
        Log.d("FRAGMENT_1", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("FRAGMENT_1", "onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("FRAGMENT_1", "onSaveInstanceState")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("FRAGMENT_1", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FRAGMENT_1", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("FRAGMENT_1", "onDetach")
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

    override fun addItem(nextNumber: Int) {
        viewModel.addItem(nextNumber)
    }

    override fun deleteItem(number: Int) {
        viewModel.deleteItem(number)
    }
}