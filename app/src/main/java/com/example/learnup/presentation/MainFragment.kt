package com.example.learnup.presentation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnup.R
import com.example.learnup.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    lateinit var binding:MainFragmentBinding
    private lateinit var vm:MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vm = ViewModelProvider(this,MainViewModelFactory( requireContext() )).get(MainViewModel::class.java)
        binding = MainFragmentBinding.inflate(inflater)
        val recView = binding.recyclerView
        recView.layoutManager = LinearLayoutManager( requireContext() )
        vm.testFillLiveData()
        recView.adapter = MyAdapter(vm)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}