package com.example.learnup.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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
            else -> currentItemLearn.description
        }
        Log.d("test1", "onBindViewHolder ${currentItemLearn.learnWord}")
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
            val defenition = holder.itemBinding.defenitionTextView
            when(defenition.isVisible){
                true ->defenition.visibility = View.INVISIBLE
                else ->defenition.visibility = View.VISIBLE
            }
            true
//            onLearnItemLongClickListener?.onLearnItemLongClicked(currentItemLearn) ?: false
        }

        holder.itemBinding.showDef.setOnClickListener {
            val chacked = holder.itemBinding.showDef.isChecked
            Log.d("test1","need to update position ${currentItemLearn.learnWord} with tag $chacked")
            vm.changeItemChack(currentItemLearn,chacked,position)
        }

//        holder.itemBinding.showDef.setOnCheckedChangeListener { compoundButton, b ->
//
//            vm.changeItemChack(currentItemLearn,b,position)
//
//            Log.d("test1","need to update position ${currentItemLearn.learnWord} with chaked $b")
//        }
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
