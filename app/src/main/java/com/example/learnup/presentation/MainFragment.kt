package com.example.learnup.presentation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnup.R
import com.example.learnup.data.LearnRepositoryImpl
import com.example.learnup.databinding.MainFragmentBinding
import com.example.learnup.domain.ItemLearn
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    lateinit var binding: MainFragmentBinding
    private lateinit var vm: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    lateinit var adapter: MyAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vm = ViewModelProvider(
            this,
            MainViewModelFactory(requireContext())
        ).get(MainViewModel::class.java)
        observeAllWords()
        binding = MainFragmentBinding.inflate(inflater)
        val recView = binding.recyclerView
        recView.layoutManager = LinearLayoutManager(requireContext())
        //       vm.testFillLiveData()
        vm.fillArr()
        adapter = MyAdapter(vm, viewLifecycleOwner)
        adapter.onLearnItemClickListener = object : MyAdapter.OnLearnItemClickListener {
            override fun onLearnItemClicked(learnItem: ItemLearn) {
                Toast.makeText(requireContext(), learnItem.learnWord, Toast.LENGTH_LONG).show()
                val bun = Bundle()
                bun.putString("learnWord", learnItem.learnWord)
                bun.putString("definition", learnItem.definition)
                bun.putString("extraDescription", learnItem.extraDescription)
                bun.putInt("id", learnItem.id)
                val newFragment = LearnItemFragment.getFragmentInstance(bun)

                this@MainFragment.parentFragmentManager.beginTransaction().addToBackStack("test")
                    .replace(
                        R.id.container, newFragment
                    ).commit()

            }

        }
        recView.adapter = adapter
        binding.button.setOnClickListener {
            GlobalScope.launch {

                LearnRepositoryImpl.getInstance(requireContext()).updateFromApi()
            }
            //vm.fillArr()
//            vm.testRep()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun observeAllWords() {
        Log.d("test1", "obsorver started at MainFragment")
        vm.dataToRecycl.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }

//            vm.dataToRecyclStateFlow.collectLatest {
//                println("inside mainFragment obsorver of StateFlow 992345678987654323456789098765432123456789098765432")
//                Log.d("test1", "inside mainFragment obsorver of StateFlow 992345678987654323456789098765432123456789098765432")
//
//                    //                adapter.updateList(it)
//
//            }

    }

}