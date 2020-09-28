package com.yts.ytscleanarchitecture.presentation.ui.books

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.google.gson.Gson
import com.yts.domain.entity.Book
import com.yts.domain.usecase.search.GetBooksUseCase
import com.yts.domain.usecase.search.GetTokenUseCase
import com.yts.ytscleanarchitecture.presentation.base.BaseViewModel

class BooksViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val getBooksUseCase: GetBooksUseCase
) : BaseViewModel() {

    private var _query = MutableLiveData<String>()
    private var _books = MutableLiveData<PagingData<Book>>()

    val books: LiveData<PagingData<Book>> get() = _books
    val query: LiveData<String> get() = _query


    @Suppress("UNCHECKED_CAST")
    fun getBooks(query: String) {
        addDisposable(
            getBooksUseCase.invoke(
                viewModelScope = viewModelScope,
                token = getTokenUseCase.invoke(),
                query
            ), onSuccess = {
                Log.e("test", Gson().toJson(it))
                _books.value = it as PagingData<Book>
            }, onError = {
                commonError(it)
            })
    }
}