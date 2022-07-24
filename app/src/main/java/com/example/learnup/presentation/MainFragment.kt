package com.example.learnup.presentation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnup.R
import com.example.learnup.databinding.MainFragmentBinding
import com.example.learnup.domain.ItemLearn

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
        vm.fillArr()
        val adapter =MyAdapter(vm,viewLifecycleOwner)
        adapter.onLearnItemClickListener = object : MyAdapter.OnLearnItemClickListener{
            override fun onLearnItemClicked(learnItem: ItemLearn) {
                Toast.makeText(requireContext(),learnItem.learnWord,Toast.LENGTH_LONG).show()
                val bun = Bundle()
                bun.putString("learnWord",learnItem.learnWord)
                bun.putString("definition",learnItem.definition)
                bun.putString("extraDescription",learnItem.extraDescription)
                bun.putInt("id",learnItem.id)
                val newFragment = LearnItemFragment.getFragmentInstance(bun)

                this@MainFragment.parentFragmentManager.beginTransaction().addToBackStack("test").replace(
                    R.id.container,newFragment).commit()

            }

        }
        recView.adapter = adapter
        binding.button.setOnClickListener {
            vm.fillArr()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}