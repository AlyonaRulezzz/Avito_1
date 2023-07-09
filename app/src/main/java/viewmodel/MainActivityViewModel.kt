package viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.avito_tech_bx_android_trainee_assigment.adapter.NumberAdapter
import com.example.avito_tech_bx_android_trainee_assigment.model.NumberModel

class MainActivityViewModel constructor(private val list: ArrayList<NumberModel>) : ViewModel() {

    lateinit var liveDataList: MutableLiveData<ArrayList<NumberModel>>
//    var liveDataList: MutableLiveData<ArrayList<NumberModel>> = MutableLiveData()

    init {
        liveDataList = MutableLiveData()
//        liveDataList.value =

    }

    fun getLiveDataObserver() : MutableLiveData<ArrayList<NumberModel>> {
        return liveDataList
    }

    fun refreshList() {
        liveDataList.postValue(list)
    }

//    private fun myNumber(): ArrayList<NumberModel>{
//        val numberList = ArrayList<NumberModel>()
//
//        lateinit var number: NumberModel
//        for (i in 1..15) {
//            number = NumberModel(i)
//            numberList.add(number)
//        }
//
//        return numberList
//    }

}