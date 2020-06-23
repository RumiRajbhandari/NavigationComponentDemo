package com.rumi.navigationcomponentdemo.screen.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumi.navigationcomponentdemo.data.repository.HomeRepository
import com.rumi.navigationcomponentdemo.model.SkuModel
import com.rumi.navigationcomponentdemo.utils.Response
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @ViewModelInject constructor(private val homeRepository: HomeRepository): ViewModel() {
    private val _skus = MutableLiveData<Response<List<SkuModel>>>()
    val skuListResponse: LiveData<Response<List<SkuModel>>>
    get() = _skus

    init {
        fetchSkuList()
    }
    fun fetchSkuList(){
        viewModelScope.launch {
            _skus.postValue(Response.loading(null))
            homeRepository.getSkuList().let {
                if (it.isSuccessful){
                    _skus.postValue(Response.success(it.body()))
                }
                else
                    _skus.postValue(Response.error(null, it.errorBody().toString()))
            }
        }
    }
}