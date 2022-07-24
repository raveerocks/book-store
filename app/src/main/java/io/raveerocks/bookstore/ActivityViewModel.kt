package io.raveerocks.bookstore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber


enum class AppState {
    LOGIN_NOT_DONE, LOGIN_DONE, WELCOME_DONE, INSTRUCTION_DONE, LOG_OUT_DONE
}

class ActivityViewModel : ViewModel() {

    val bookItems: LiveData<List<BookItem>>
        get() = _bookItems
    private var _bookItems: MutableLiveData<List<BookItem>> = MutableLiveData()
    private var list: ArrayList<BookItem> = ArrayList()
    private var appState: AppState = AppState.LOGIN_NOT_DONE

    init {
        _bookItems.value = list
    }

    fun setState(appState: AppState) {
        Timber.i("State changed from " + this.appState + " to " + appState)
        this.appState = appState
    }

    fun getState(): AppState {
        return this.appState
    }

    fun addBook(bookItem: BookItem) {
        list.add(bookItem)
    }

}