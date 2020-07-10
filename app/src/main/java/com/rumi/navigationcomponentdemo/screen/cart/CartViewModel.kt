package com.rumi.navigationcomponentdemo.screen.cart

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumi.navigationcomponentdemo.data.repository.CartRepository
import com.rumi.navigationcomponentdemo.data.model.SkuModel
import com.rumi.navigationcomponentdemo.utils.Response
import kotlinx.coroutines.launch

class CartViewModel @ViewModelInject constructor(private val cartRepository: CartRepository) : ViewModel() {
    private val _carts = MutableLiveData<Response<List<SkuModel>>>()
    val cartListResponse: LiveData<Response<List<SkuModel>>>
        get() = _carts

    init {
        fetchSkuList()
    }

    fun fetchSkuList() {
        viewModelScope.launch {
            _carts.postValue(Response.loading(null))
            cartRepository.getCartList().let {
                if (it.isSuccessful) {
                    _carts.postValue(Response.success(it.body()))
                } else
                    _carts.postValue(Response.error(null, it.errorBody().toString()))
            }
        }
    }
}