package com.example.learnup.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.learnup.databinding.EditLearnItemBinding
import com.example.learnup.databinding.ItemFullFragmentBinding

class EditLearnItemFragment:Fragment() {
    lateinit var binding:EditLearnItemBinding
    lateinit var vm:EditLearnItemViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm = EditLearnItemVMFactory(requireActivity().application).create(EditLearnItemViewModel::class.java)
        var id = requireArguments().getInt("id")
        if(id !=0 ){ vm.getLearnItem(id) }
        binding = EditLearnItemBinding.inflate(inflater,container,false)
        binding.testTextView.text = vm.itemView.value?.learnWord
        binding.etLearnWord.setText(vm.itemView.value?.learnWord)

        return binding.root
    }

    companion object{
        fun getFragmentInstance(args: Bundle): Fragment {
            val fragment = EditLearnItemFragment()
            fragment.arguments = args
            return fragment
        }
    }
}