package com.example.learnup.presentation.edit_learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.learnup.databinding.EditLearnItemBinding
import com.example.learnup.presentation.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditLearnItemFragment: BaseFragment<EditLearnItemBinding>() {
    private val vm: EditLearnItemViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var id = requireArguments().getInt("id")
        if(id !=0 ){ vm.getLearnItem(id) }

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