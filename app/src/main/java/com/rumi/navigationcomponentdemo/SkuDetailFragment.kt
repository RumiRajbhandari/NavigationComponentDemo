package com.rumi.navigationcomponentdemo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.evolve.rosiautils.PictureManager
import com.evolve.rosiautils.TYPE_ERROR
import com.evolve.rosiautils.TYPE_SUCCESS
import com.evolve.rosiautils.loadImage
import com.evolve.rosiautils.showToast
import kotlinx.android.synthetic.main.fragment_sku_detail.*

class SkuDetailFragment : Fragment() {

    private lateinit var pictureManager: PictureManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sku_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pictureManager = PictureManager(this)
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