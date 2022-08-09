package com.example.learnup.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.example.learnup.R
import com.example.learnup.databinding.ItemFullFragmentBinding
import com.example.learnup.domain.models.AppSettings
import com.example.learnup.domain.models.ItemLearn
import com.example.learnup.presentation.vmFactories.LearnItemVMFactory
import com.example.learnup.presentation.viewModels.LearnItemViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LearnItemFragment:Fragment() {

        lateinit var binding:ItemFullFragmentBinding
        lateinit var vm: LearnItemViewModel
    init {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        vm = LearnItemVMFactory(requireActivity().application).create(LearnItemViewModel::class.java)
//        vm.itemView.observe(viewLifecycleOwner){
//            binding.tvDescription.text = it.description
//        }
        vm.getLearnItem(requireArguments().getInt("id"))
        binding = ItemFullFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("test1","onViewCreated started, line 41")
        vm.itemView.observe(viewLifecycleOwner){
            fillLayoutWithData(it)

        }
        binding.btnNext.setOnClickListener {
            vm.nextItem()
        }
        binding.btnPrev.setOnClickListener {
            vm.prevItem()
        }
        binding.btnEdit.setOnClickListener{
            val bun = Bundle()
            bun.putInt("id", vm.itemView.value!!.id)
            launchFragment(EditLearnItemFragment.getFragmentInstance(bun))
        }

        binding.btnDelete.setOnClickListener{
            vm.deleteCurrentItem()
        }

        vm.viewModelScope.launch {
            vm.settings.collectLatest {
                applySettings()
                fillLayoutWithData(vm.itemView.value!!)
            }
        }


        Log.d("test1","onViewCreated, after itemView observer, line 45")
        super.onViewCreated(view, savedInstanceState)
    }

    fun applySettings(){
        var toShow = when (vm.settings.value.wayOfLearn) {
            AppSettings.SHOW_ALL -> View.VISIBLE
            else -> View.INVISIBLE
        }
        binding.tvDescription.visibility = toShow
        binding.tvLink.visibility = toShow
        binding.tvExtraDescription.visibility = toShow

    }

    fun fillLayoutWithData(it:ItemLearn){
        var word = when (vm.settings.value.wayOfLearn){
            AppSettings.SHOW_DESCRIPTION -> it.description
            else -> it.learnWord
        }
        var description = when (vm.settings.value.wayOfLearn){
            AppSettings.SHOW_DESCRIPTION -> it.learnWord
            else -> it.description
        }
        binding.tvExtraDescription.text = it.extraDescription
        binding.tvLearnWord.text = word
        binding.tvDescription.text = description
        binding.tvLink.text = it.link
        applySettings()
    }

    private fun launchFragment(fragment:Fragment){
        parentFragmentManager.beginTransaction().addToBackStack("test")
            .replace(
                R.id.container, fragment
            ).commit()
    }


    companion object{
        fun getFragmentInstance(args:Bundle):Fragment{
            val fragment = LearnItemFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
