package com.rumi.navigationcomponentdemo.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.rumi.navigationcomponentdemo.R
import com.rumi.navigationcomponentdemo.databinding.FragmentHomeBinding
import com.rumi.navigationcomponentdemo.data.model.SkuModel
import com.rumi.navigationcomponentdemo.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    val sku = SkuModel(1, "Tiger Biscuit", 10f)
    lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        observeSkuListResponse()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_detail.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.imgSku to "imageView"
            )
            val action =
                HomeFragmentDirections.actionTodayFragmentToSkuDetailFragment(
                    sku
                )
            findNavController().navigate(action,extras)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    private fun observeSkuListResponse(){
        homeViewModel.skuListResponse.observe(this, Observer {
            when(it.status){
                Status.LOADING->{
                    println("loading data")

                }
                Status.SUCCESS -> {
                    it.data?.let {

                        println("sku list success $it")
                    }

                }
                Status.ERROR ->{

                    println("error")

                }
            }
        })
    }
}
