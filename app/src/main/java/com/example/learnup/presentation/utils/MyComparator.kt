package com.example.learnup.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.learnup.network.data.LearnItemDataClass

class MyComparator : DiffUtil.ItemCallback<LearnItemDataClass>() {
    override fun areItemsTheSame(oldItem: LearnItemDataClass, newItem: LearnItemDataClass): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LearnItemDataClass, newItem: LearnItemDataClass): Boolean {
        return oldItem == newItem
    }
}