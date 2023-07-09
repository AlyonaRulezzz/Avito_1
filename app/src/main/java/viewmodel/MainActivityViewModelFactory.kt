package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.avito_tech_bx_android_trainee_assigment.model.NumberModel

class MainActivityViewModelFactory constructor(private val list: ArrayList<NumberModel>) :
ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            MainActivityViewModel(this.list) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}