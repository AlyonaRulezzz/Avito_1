package viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.avito_tech_bx_android_trainee_assigment.model.NumberModel

class MainActivityViewModel : ViewModel() {

    var liveDataList: MutableLiveData<ArrayList<NumberModel>> = MutableLiveData()

    init {
        liveDataList.value = myNumber()
    }

    fun getLiveDataObserver() : MutableLiveData<ArrayList<NumberModel>> {
        return liveDataList
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