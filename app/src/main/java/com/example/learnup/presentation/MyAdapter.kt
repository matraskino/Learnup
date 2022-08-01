package com.example.learnup.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.learnup.databinding.ItemViewBinding
import com.example.learnup.domain.ItemLearn
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MyAdapter(private var vm: MainViewModel, private val owner:LifecycleOwner): RecyclerView.Adapter<MyAdapter.UserViewHolder>(), View.OnClickListener {

        var dataToRecycl = vm.dataToRecyclStateFlow.value////dataToRecycl.value!!
    var onLearnItemClickListener:OnLearnItemClickListener? = null
    var onLearnItemLongClickListener:OnLearnItemLongClickListener? = null

    init {
//        vm.dataToRecycl.observe(owner, Observer {
//            //updateList(it)
//        })
//    updateData()

    }

    fun updateData(){
        GlobalScope.launch {
            vm.dataToRecyclStateFlow.collectLatest {
                dataToRecycl = it
            }
            notifyDataSetChanged()
        }
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
        holder.itemBinding.defenitionTextView.text = currentItemLearn.description
        holder.itemBinding.showDef.isChecked = currentItemLearn.isChecked
        holder.itemView.setOnClickListener {
            onLearnItemClickListener?.onLearnItemClicked(currentItemLearn)
        }

        holder.itemView.setOnLongClickListener {
            onLearnItemLongClickListener?.onLearnItemLongClicked(currentItemLearn) ?: false

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
    interface OnLearnItemLongClickListener{
        fun onLearnItemLongClicked(learnItem:ItemLearn):Boolean
    }

}
