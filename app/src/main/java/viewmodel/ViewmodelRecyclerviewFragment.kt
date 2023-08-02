package viewmodel

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.avito_tech_bx_android_trainee_assigment.R
import com.example.avito_tech_bx_android_trainee_assigment.adapter.NumberAdapter
import com.example.avito_tech_bx_android_trainee_assigment.databinding.FragmentViewmodelRecyclerviewBinding
import com.example.avito_tech_bx_android_trainee_assigment.model.NumberModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ViewmodelRecyclerviewFragment : Fragment() {

    private val adapter: NumberAdapter = NumberAdapter(::adapterListener)

    private val binding: FragmentViewmodelRecyclerviewBinding by lazy {
        FragmentViewmodelRecyclerviewBinding.inflate(layoutInflater)
    }

    private var factory = ViewmodelRecyclerviewViewModelFactory(loadListFromSharedPreferences(requireContext(), "listOfNumberModel"))

    private lateinit var viewModel: ViewmodelRecyclerviewViewModel
//    private val viewModel by lazy {
//    viewModel = ViewModelProviders.of(this, factory).get(ViewmodelRecyclerviewViewModel::class.java)
//        ViewModelProviders.of(this, factory).get(ViewmodelRecyclerviewViewModel::class.java)
//        ViewModelProviders.of(this, ViewmodelRecyclerviewViewModelFactory(loadListFromSharedPreferences(requireContext(), "listOfNumberModel"))).get(ViewmodelRecyclerviewViewModel::class.java)
//    }

    private val sharedPreferences: SharedPreferences by lazy {
        requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }
    private val editor: SharedPreferences.Editor by lazy {
        sharedPreferences.edit()
    }
//    private lateinit var viewModel: ViewmodelRecyclerviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this, factory).get(ViewmodelRecyclerviewViewModel::class.java)

        return inflater.inflate(R.layout.fragment_viewmodel_recyclerview, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        setContentView(binding.root)
        binding.rvNumber.adapter = adapter
        binding.rvNumber.layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            GridLayoutManager(context, 2)
        } else {
            GridLayoutManager(context, 4)
        }
        initViewModel()
    }

    private fun initViewModel() {
        Log.d("MY_LOG_on_create", sharedPreferences.getString("listOfNumberModel", null).toString())

        viewModel._liveDataList.observe(viewLifecycleOwner) {
            it?.let { adapter.setList(it) }
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
            }
            .setNegativeButton("Nope") {_, _ ->
                Toast.makeText(context, "Good choice, pal)", Toast.LENGTH_SHORT).show()
            }
            .create()
            .show()
    }


    override fun onStop() {
        saveListToSharedPreferences(requireContext(), viewModel.liveDataList.value!!, "listOfNumberModel")
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