package com.rumi.navigationcomponentdemo.screen.home

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.rosia.base.BaseAdapter
import com.rumi.navigationcomponentdemo.R
import com.rumi.navigationcomponentdemo.base.BaseViewHolder
import com.rumi.navigationcomponentdemo.data.model.SkuModel
import com.rumi.navigationcomponentdemo.databinding.ItemSkuBinding

class SkuAdapter(
    private var dataList: List<SkuModel>,
    private val onItemClicked: (SkuModel, ImageView) -> Unit

) : BaseAdapter<SkuModel, SkuAdapter.SkuViewHolder>() {

    override fun getViewHolder(binding: ViewDataBinding, viewType: Int): SkuViewHolder {
        return SkuViewHolder(binding)
    }

    override fun onBindCustomViewHolder(holder: SkuViewHolder, position: Int) {
        val item = dataList[position]
        holder.bindView(item)
    }

    override fun getLayoutResource(viewType: Int): Int = R.layout.item_sku

    override fun getItemCount(): Int = dataList.size

    fun updateData(dataList: List<SkuModel>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class SkuViewHolder(private val binding: ViewDataBinding) : BaseViewHolder<SkuModel>(binding) {
        override fun bindView(item: SkuModel) {
            super.bindView(item)
            if (binding is ItemSkuBinding) {
                val binding = binding as ItemSkuBinding
                binding.imgSku.transitionName = item.id.toString()
                binding.container.setOnClickListener { onItemClicked(item, binding.imgSku) }
            }
        }
    }
}