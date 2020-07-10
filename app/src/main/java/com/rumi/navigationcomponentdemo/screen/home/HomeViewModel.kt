package com.rumi.navigationcomponentdemo.screen.home

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumi.navigationcomponentdemo.data.mapper.SkuMapper
import com.rumi.navigationcomponentdemo.data.model.SkuModel
import com.rumi.navigationcomponentdemo.data.repository.HomeRepository
import com.rumi.navigationcomponentdemo.utils.Response
import com.rumi.navigationcomponentdemo.utils.checkNetworkAvailability
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    var app: Application,
    private val homeRepository: HomeRepository) : ViewModel() {
    private val _skus = MutableLiveData<Response<List<SkuModel>>>()
    val skuListResponse: LiveData<Response<List<SkuModel>>>
        get() = _skus

    init {
        fetchSkuList()
    }

    fun fetchSkuList() {
        viewModelScope.launch {
            if (checkNetworkAvailability(app)){
                _skus.postValue(Response.loading(null))
                homeRepository.getSkuList().let {
                    if (it.isSuccessful) {
                        val response = it.body()
                        val skuList = SkuMapper.mapToDomain(response)
                        _skus.postValue(Response.success(skuList))
                    } else
                        _skus.postValue(Response.error(null, it.errorBody().toString()))
                }
            }
        }
    }
}