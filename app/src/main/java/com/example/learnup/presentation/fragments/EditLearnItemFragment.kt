package com.example.learnup.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.learnup.databinding.EditLearnItemBinding
import com.example.learnup.presentation.vmFactories.EditLearnItemVMFactory
import com.example.learnup.presentation.viewModels.EditLearnItemViewModel

class EditLearnItemFragment:Fragment() {
    lateinit var binding:EditLearnItemBinding
    lateinit var vm: EditLearnItemViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm = EditLearnItemVMFactory(requireActivity().application).create(EditLearnItemViewModel::class.java)
        var id = requireArguments().getInt("id")
        if(id !=0 ){ vm.getLearnItem(id) }
        binding = EditLearnItemBinding.inflate(inflater,container,false)
        binding.etLearnWord.setText(vm.itemView.value?.learnWord)
        binding.etDescription.setText(vm.itemView.value?.description)
        binding.etLink.setText(vm.itemView.value?.link)
        binding.etExtraDescription.setText(vm.itemView.value?.extraDescription)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.saveButton.setOnClickListener {
            val learnWord = binding.etLearnWord.text.toString()
            val description = binding.etDescription.text.toString()
            val link = binding.etLink.text.toString()
            val extraDescription = binding.etExtraDescription.text.toString()
            vm.saveLearnItem(learnWord = learnWord, description = description, link = link, extraDescription = extraDescription)
            requireActivity().onBackPressed()
        }
        super.onViewCreated(view, savedInstanceState)
    }



    companion object{
        fun getFragmentInstance(args: Bundle): Fragment {
            val fragment = EditLearnItemFragment()
            fragment.arguments = args
            return fragment
        }
    }
}