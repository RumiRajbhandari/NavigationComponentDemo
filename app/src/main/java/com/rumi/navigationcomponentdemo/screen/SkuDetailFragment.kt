package com.rumi.navigationcomponentdemo.screen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.evolve.rosiautils.PictureManager
import com.evolve.rosiautils.TYPE_ERROR
import com.evolve.rosiautils.TYPE_SUCCESS
import com.evolve.rosiautils.loadImage
import com.evolve.rosiautils.showToast
import com.rumi.navigationcomponentdemo.R
import com.rumi.navigationcomponentdemo.databinding.FragmentSkuDetailBinding
import kotlinx.android.synthetic.main.fragment_sku_detail.*

class SkuDetailFragment : Fragment() {

    lateinit var binding: FragmentSkuDetailBinding
    private lateinit var pictureManager: PictureManager
    val safeArgs: SkuDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sku_detail, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pictureManager = PictureManager(this)
        val sku = safeArgs.sku
        binding.item = sku
        binding.imgSku.transitionName = sku?.id.toString()

        btn_take_pic.setOnClickListener {
            context?.let {
                if (pictureManager.hasPermission(it)) {
                    openCamera()
                }
            }
        }
        btn_logout.setOnClickListener {
            findNavController().navigate(R.id.action_skuDetailFragment_to_loginFragment)
        }

        binding.btnBuy.setOnClickListener {
            findNavController().navigate(R.id.action_skuDetailFragment_to_paymentFragment)
        }

        getDataFromPreviousFragment()
    }

    private fun openCamera() {
        pictureManager.startCameraIntent(requireContext()) { imgPath ->
            loadImage(img_sku, imgPath) {
                if (!it) {
                    showToast("something went wrong", TYPE_ERROR)
                } else
                    showToast("success", TYPE_SUCCESS)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (pictureManager.onRequestPermissionsResult(requestCode, permissions, grantResults)) openCamera()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        pictureManager.onActivityResult(requestCode, resultCode, data)
    }

    // Similar to onActivityResult
    private fun getDataFromPreviousFragment() {
        val result = findNavController().currentBackStackEntry?.savedStateHandle?.get<String>("data")
        if (!result.isNullOrEmpty())
            showToast(result, TYPE_SUCCESS)
    }
}