package com.rumi.navigationcomponentdemo.screen.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rumi.navigationcomponentdemo.R
import com.rumi.navigationcomponentdemo.databinding.FragmentCartBinding
import com.rumi.navigationcomponentdemo.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private val cartViewModel: CartViewModel by viewModels()
    lateinit var binding: FragmentCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        observeCartListResponse()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_cart, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)
    }

    private fun observeCartListResponse() {
        cartViewModel.cartListResponse.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    println("loading data")
                }
                Status.SUCCESS -> {
                    it.data?.let {

                        println("sku list success $it")
                    }
                }
                Status.ERROR -> {

                    println("error")
                }
            }
        })
    }
}