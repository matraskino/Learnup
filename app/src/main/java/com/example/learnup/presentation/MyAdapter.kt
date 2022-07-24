package com.example.learnup.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.learnup.databinding.ItemViewBinding
import com.example.learnup.domain.ItemLearn


class MyAdapter(private var vm: MainViewModel, private val owner:LifecycleOwner): RecyclerView.Adapter<MyAdapter.UserViewHolder>(), View.OnClickListener {

        var dataToRecycl = vm.dataToRecycl.value!!
    var onLearnItemClickListener:OnLearnItemClickListener? = null

    init {
        vm.dataToRecycl.observe(owner, Observer {
            updateList(it)
        })

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
        holder.itemView.setOnClickListener {
            onLearnItemClickListener?.onLearnItemClicked(currentItemLearn)

        }




    }

    override fun getItemCount(): Int {
       return dataToRecycl.size
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    fun updateList(list:List<ItemLearn>){

        dataToRecycl = list
        notifyDataSetChanged()
    }

    interface OnLearnItemClickListener{
        fun onLearnItemClicked(learnItem:ItemLearn)
    }

}
