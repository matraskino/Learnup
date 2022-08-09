package com.example.learnup.presentation.utils

import android.content.Context
import android.widget.ImageView

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

open class BaseFragment <T : ViewBinding> : Fragment() {

    protected var _binding: T? = null
    protected val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}