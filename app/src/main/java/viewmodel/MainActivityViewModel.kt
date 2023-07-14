package viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.avito_tech_bx_android_trainee_assigment.adapter.NumberAdapter
import com.example.avito_tech_bx_android_trainee_assigment.model.NumberModel
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivityViewModel: ViewModel() {

    private val _liveDataList = MutableLiveData<List<NumberModel>>(myNumber())
    val liveDataList: LiveData<List<NumberModel>> get() = _liveDataList

    private val deletedPool: Queue<Int> = LinkedList()

    init {
        addItem()
    }

    fun deleteItem(number: Int) {
        deletedPool.add(number) // TODO обернуть в корутину
        val list = _liveDataList.value?.filter { it.number != number } ?: error("") //TODO
        _liveDataList.value = list
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

    private fun addItem() {
        var i = 16
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                CoroutineScope(Dispatchers.Main).launch {
                    while (true) {
                        delay(2_000)
                        val list = _liveDataList.value?.toMutableList() ?: error("") //TODO
                        if (deletedPool.isEmpty()) {
                            list.add(NumberModel(i))
                            i++
                        } else {
                            list.add(NumberModel(deletedPool.remove()))
                        }
                        _liveDataList.value = list
                    }
                }
            }
        }
    }
}