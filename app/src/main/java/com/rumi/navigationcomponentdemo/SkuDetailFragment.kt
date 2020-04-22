package com.rumi.navigationcomponentdemo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.evolve.rosiautils.PictureManager
import com.evolve.rosiautils.TYPE_ERROR
import com.evolve.rosiautils.TYPE_SUCCESS
import com.evolve.rosiautils.loadImage
import com.evolve.rosiautils.showToast
import com.rumi.navigationcomponentdemo.databinding.FragmentSkuDetailBinding
import kotlinx.android.synthetic.main.fragment_sku_detail.*

class SkuDetailFragment : Fragment() {

    lateinit var binding: FragmentSkuDetailBinding
    private lateinit var pictureManager: PictureManager
    val safeArgs : SkuDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sku_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pictureManager = PictureManager(this)
        binding.item = safeArgs.sku

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
            findNavController().navigate(R.id.action_skuDetailFragment_to_cartFragment)
        }
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
}