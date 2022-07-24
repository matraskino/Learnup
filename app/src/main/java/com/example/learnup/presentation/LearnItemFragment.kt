package com.example.learnup.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.learnup.databinding.ItemFullFragmentBinding

class LearnItemFragment:Fragment() {

        lateinit var binding:ItemFullFragmentBinding
    init {
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ItemFullFragmentBinding.inflate(inflater,container,false)

        binding.tvDescription.text = requireArguments().getString("definition","there are no valye")
        binding.tvLearnWord.text = requireArguments().getString("learnWord","there are no valye")
        binding.tvExtraDescription.text = requireArguments().getString("extraDescription","there are no valye")
        return binding.root
//        return super.onCreateView(inflater, container, savedInstanceState)
    }
    companion object{
        fun getFragmentInstance(args:Bundle):Fragment{
            val fragment = LearnItemFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
