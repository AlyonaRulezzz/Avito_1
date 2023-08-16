package com.example.avito_tech_bx_android_trainee_assigment.fragments.contract

import androidx.fragment.app.Fragment

fun Fragment.navigator(): Navigator {
    return parentFragment as? Navigator ?: error("not impl")
}

interface Navigator {
    fun addItem(nextNumber: Int)
    fun deleteItem(number: Int)
}