package viewmodel

import android.annotation.SuppressLint
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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.avito_tech_bx_android_trainee_assigment.R
//import com.example.avito_tech_bx_android_trainee_assigment.adapter.NumberAdapter
import com.example.avito_tech_bx_android_trainee_assigment.databinding.FragmentViewmodelRecyclerviewBinding
import com.example.avito_tech_bx_android_trainee_assigment.databinding.ItemNumberLayoutBinding
import com.example.avito_tech_bx_android_trainee_assigment.model.NumberModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.item_number_layout.view.*


class ViewmodelRecyclerviewFragment : Fragment() {

    class NumberAdapter(
        val listener: (value: Int) -> Unit,
    ): RecyclerView.Adapter<NumberAdapter.NumberViewHolder>() {


        private var numberList = emptyList<NumberModel>()

        private var i = 0

        inner class NumberViewHolder(val binding: ItemNumberLayoutBinding): RecyclerView.ViewHolder(binding.root) {

            private val context = binding.root.context

            fun bind(value: NumberModel) {
                itemView.tv_number.text = value.number.toString()
                val res = ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete_24)
                itemView.iv_cancel.setImageDrawable(res)
                itemView.iv_cancel.setOnClickListener {
                    listener(value.number)
                    notifyItemRemoved(layoutPosition)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
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
////////////////////////////////////////
    private val adapter: NumberAdapter = NumberAdapter(::adapterListener)

//    private val binding: FragmentViewmodelRecyclerviewBinding by lazy {
//        FragmentViewmodelRecyclerviewBinding.inflate(layoutInflater)
//    }
    lateinit var binding: FragmentViewmodelRecyclerviewBinding

    private lateinit var factory: ViewmodelRecyclerviewViewModelFactory
//    var factory = ViewmodelRecyclerviewViewModelFactory(loadListFromSharedPreferences(requireContext(), "listOfNumberModel"))

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
        factory = ViewmodelRecyclerviewViewModelFactory(loadListFromSharedPreferences(requireContext(), "listOfNumberModel"))
//    private val viewModel by lazy {
        viewModel = ViewModelProvider(this, factory).get(ViewmodelRecyclerviewViewModel::class.java)
//    }

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

        return rootView
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
//    override fun onCreate(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
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


    override fun onDestroyView() {
        saveListToSharedPreferences(requireContext(), viewModel.liveDataList.value!!, "listOfNumberModel")
        Log.d("MY_LOG_on_stop", sharedPreferences.getString("listOfNumberModel", null).toString())
        super.onDestroyView()
    }

//    override fun onStop() {
////        saveListToSharedPreferences(requireContext(), viewModel.liveDataList.value!!, "listOfNumberModel")
//        Log.d("MY_LOG_on_stop", sharedPreferences.getString("listOfNumberModel", null).toString())
//        super.onStop()
//    }
//
//    override fun onDestroy() {
//        Log.d("MY_LOG_on_destroy", sharedPreferences.getString("listOfNumberModel", null).toString())
//        super.onDestroy()
//    }

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