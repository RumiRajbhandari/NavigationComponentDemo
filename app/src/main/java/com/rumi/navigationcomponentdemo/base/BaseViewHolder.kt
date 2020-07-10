package com.rumi.navigationcomponentdemo.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.rumi.navigationcomponentdemo.BR

abstract class BaseViewHolder<in T>(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    open fun bindView(item: T) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}