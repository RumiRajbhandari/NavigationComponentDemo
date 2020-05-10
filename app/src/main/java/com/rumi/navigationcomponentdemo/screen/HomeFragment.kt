package com.rumi.navigationcomponentdemo.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.rumi.navigationcomponentdemo.R
import com.rumi.navigationcomponentdemo.databinding.FragmentHomeBinding
import com.rumi.navigationcomponentdemo.model.SkuModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    val sku = SkuModel(1, "Tiger Biscuit", 10f)
    lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
}
