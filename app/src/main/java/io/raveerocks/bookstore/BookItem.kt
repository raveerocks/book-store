package io.raveerocks.bookstore

import androidx.lifecycle.MutableLiveData

class BookItem {

    val title: MutableLiveData<String?> = MutableLiveData("")
    val author: MutableLiveData<String?> = MutableLiveData("")
    val category: MutableLiveData<String?> = MutableLiveData("")
    val isbn: MutableLiveData<String?> = MutableLiveData("")
    val publisher: MutableLiveData<String?> = MutableLiveData("")
    val language: MutableLiveData<String?> = MutableLiveData("")
    val price: MutableLiveData<String?> = MutableLiveData("")
    val rating: MutableLiveData<Float?> = MutableLiveData(0F)

    fun formattedPrice() = "$${this.price.value!!.format(2)}"
}