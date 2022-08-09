package com.example.learnup.presentation.learn_recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.learnup.databinding.ItemViewBinding
import com.example.learnup.network.data.LearnItemDataClass
import com.example.learnup.presentation.utils.MyComparator

//реалізувати ліст адаптер для ресайклера з MyComparator()

class MyAdapter(
    private val onClickItem: (LearnItemDataClass) -> Unit,
) : ListAdapter<LearnItemDataClass, RecyclerView.ViewHolder>(MyComparator()) {


    class UserViewHolder(val itemBinding: ItemViewBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind() {

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemBinding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return UserViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

//    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
//
//
////        val currentItemLearn: ItemLearn = dataToRecycl[position]
////        holder.itemBinding.firstText.text = currentItemLearn.learnWord
////        holder.itemBinding.defenitionTextView.text = currentItemLearn.description
////        holder.itemBinding.showDef.isChecked = currentItemLearn.isChecked
////        holder.itemView.setOnClickListener {
////            onLearnItemClickListener?.onLearnItemClicked(currentItemLearn)
//    }

//        holder.itemView.setOnLongClickListener {
////            onLearnItemLongClickListener?.onLearnItemLongClicked(currentItemLearn) ?: false
//
//        }



}
