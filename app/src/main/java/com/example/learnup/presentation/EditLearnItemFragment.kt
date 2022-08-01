package com.example.learnup.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.learnup.databinding.EditLearnItemBinding
import com.example.learnup.databinding.ItemFullFragmentBinding

class EditLearnItemFragment:Fragment() {
    lateinit var binding:EditLearnItemBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var args = requireArguments().getInt("id")
        binding = EditLearnItemBinding.inflate(inflater,container,false)

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