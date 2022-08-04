package com.example.learnup.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.learnup.databinding.ItemFullFragmentBinding
import com.example.learnup.presentation.vmFactories.LearnItemVMFactory
import com.example.learnup.presentation.viewModels.LearnItemViewModel

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

        vm.itemView.observe(viewLifecycleOwner){
            binding.tvDescription.text = it.description
        }
        vm.getLearnItem(requireArguments().getInt("id"))
        binding = ItemFullFragmentBinding.inflate(inflater,container,false)


//        binding.tvDescription.text = requireArguments().getString("definition","there are no valye")
//        binding.tvLearnWord.text = requireArguments().getString("learnWord","there are no valye")
//        binding.tvExtraDescription.text = requireArguments().getString("extraDescription","there are no valye")
        return binding.root
//        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("test1","onViewCreated started, line 41")
        vm.itemView.observe(viewLifecycleOwner){
            binding.tvExtraDescription.text = it.extraDescription
            binding.tvLearnWord.text = it.learnWord
            binding.tvDescription.text = it.description
            binding.tvLink.text = it.link

        }
        binding.btnNext.setOnClickListener {
            vm.nextItem()
        }
        binding.btnPrev.setOnClickListener {
            vm.prevItem()
        }


        Log.d("test1","onViewCreated, after itemView observer, line 45")
        super.onViewCreated(view, savedInstanceState)
    }


    companion object{
        fun getFragmentInstance(args:Bundle):Fragment{
            val fragment = LearnItemFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
