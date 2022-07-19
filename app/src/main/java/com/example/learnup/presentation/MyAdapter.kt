package com.example.learnup.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.learnup.databinding.ItemViewBinding
import com.example.learnup.domain.ItemLearn


class MyAdapter(vm: MainViewModel): RecyclerView.Adapter<MyAdapter.UserViewHolder>(), View.OnClickListener {

        var dataToRecycl = vm.dataToRecycl.value!!

    init {
    }

    class UserViewHolder(val itemBinding: ItemViewBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(){

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemBinding = ItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItemLearn: ItemLearn = dataToRecycl[position]
        holder.itemBinding.firstText.text = currentItemLearn.learnWord
        holder.itemBinding.defenitionTextView.text = currentItemLearn.definition
        holder.itemBinding.showDef.isChecked = currentItemLearn.toLearn


    }

    override fun getItemCount(): Int {
       return dataToRecycl.size
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}
