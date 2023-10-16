package com.catnip.retrofitexample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {
    val service: ProductService by lazy {
        ProductService.invoke()
    }
    val responseLiveData = MutableLiveData<ProductsResponse>()

    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = service.getProducts()
                responseLiveData.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}