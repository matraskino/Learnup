package com.example.learnup.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.learnup.databinding.ItemViewBinding
import com.example.learnup.domain.models.AppSettings
import com.example.learnup.domain.models.ItemLearn
import com.example.learnup.presentation.viewModels.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MyAdapter(private var vm: MainViewModel, private val owner:LifecycleOwner): RecyclerView.Adapter<MyAdapter.UserViewHolder>(), View.OnClickListener {

    var dataToRecycl = vm.dataToRecycl.value!!
    var onLearnItemClickListener:OnLearnItemClickListener? = null
    var onLearnItemLongClickListener:OnLearnItemLongClickListener? = null
    var settings = vm.settings



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
        var word = when (settings.value.wayOfLearn){
            AppSettings.SHOW_DESCRIPTION -> currentItemLearn.description
            else -> currentItemLearn.learnWord
        }
        var description = when (settings.value.wayOfLearn){
            AppSettings.SHOW_DESCRIPTION -> currentItemLearn.learnWord
            else -> currentItemLearn.learnWord
        }
        holder.itemBinding.firstText.text = word
        holder.itemBinding.defenitionTextView.text = description
        holder.itemBinding.showDef.isChecked = currentItemLearn.isChecked
        holder.itemView.setOnClickListener {
            onLearnItemClickListener?.onLearnItemClicked(currentItemLearn)
        }
        holder.itemBinding.defenitionTextView.visibility = when (settings.value.wayOfLearn){
            AppSettings.SHOW_ALL -> View.VISIBLE
            else -> View.INVISIBLE
        }

        holder.itemView.setOnLongClickListener {
            false
//            onLearnItemLongClickListener?.onLearnItemLongClicked(currentItemLearn) ?: false
        }

    }

    override fun getItemCount(): Int {
       return dataToRecycl.size
    }

    fun observeSettings(){
        vm.viewModelScope.launch {
            settings.collectLatest {
                notifyDataSetChanged()
            }
        }
    }


    override fun onClick(p0: View?) {

        TODO("Not yet implemented")
    }

    fun updateList(list:MutableList<ItemLearn>){

        dataToRecycl = list
        notifyDataSetChanged()
    }

    interface OnLearnItemClickListener{
        fun onLearnItemClicked(learnItem: ItemLearn)
    }
    interface OnLearnItemLongClickListener{
        fun onLearnItemLongClicked(learnItem: ItemLearn):Boolean
    }

}
