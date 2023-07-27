package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.avito_tech_bx_android_trainee_assigment.model.NumberModel

class MainActivityViewModelFactory: ViewModelProvider.Factory {
    private var mParam: List<NumberModel>? = null

    constructor(l: List<NumberModel>) {
        mParam = l
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            MainActivityViewModel(mParam!!) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}