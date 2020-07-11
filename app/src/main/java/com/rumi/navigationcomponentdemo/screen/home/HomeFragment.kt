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
import com.rumi.navigationcomponentdemo.data.model.SkuModel
import com.rumi.navigationcomponentdemo.databinding.FragmentHomeBinding
import com.rumi.navigationcomponentdemo.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    lateinit var binding: FragmentHomeBinding
    var adapter: SkuAdapter? = null
    var skuList: List<SkuModel> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        observeSkuListResponse()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (::binding.isInitialized) return binding.root
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (adapter == null) initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = SkuAdapter(skuList, onItemClicked = { sku, imageView, textView ->
            val extras = FragmentNavigatorExtras(
                imageView to sku.id.toString(),
                textView to "${sku.id}_title"
            )
            val action =
                HomeFragmentDirections.actionTodayFragmentToSkuDetailFragment(
                    sku
                )
            findNavController().navigate(action, extras)
        })
        binding.rvSku.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    private fun observeSkuListResponse() {
        homeViewModel.skuListResponse.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    println("loading data")
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        this.skuList = it
                        adapter?.updateData(skuList)
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
