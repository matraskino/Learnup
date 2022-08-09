package com.example.learnup.presentation

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnup.R
import com.example.learnup.databinding.MainFragmentBinding
import com.example.learnup.domain.ItemLearn
import com.example.learnup.network.data.LearnItemDataClass
import com.example.learnup.presentation.edit_learn.EditLearnItemFragment
import com.example.learnup.presentation.learn_item.LearnItemFragment
import com.example.learnup.presentation.learn_recycler.MyAdapter
import com.example.learnup.presentation.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<MainFragmentBinding>() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var application: Application
    private lateinit var communitiesAdapter: MyAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = MainFragmentBinding.inflate(inflater)
        application = requireActivity().application
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllListInfo()
        setUpClickListeners()
        initUI()
    }

    private fun initUI() {
        observeAllWords()
        initRecyclerView()

        //vm.testFillLiveData()
//        vm.fillArr()


//        adapter = MyAdapter(vm, viewLifecycleOwner)
//        adapter.onLearnItemClickListener = object : MyAdapter.OnLearnItemClickListener {
//            override fun onLearnItemClicked(learnItem: ItemLearn) {
//                Toast.makeText(requireContext(), learnItem.learnWord, Toast.LENGTH_LONG).show()
//                val bun = Bundle()
//                bun.putString("learnWord", learnItem.learnWord)
//                bun.putString("definition", learnItem.description)
//                bun.putString("extraDescription", learnItem.extraDescription)
//                bun.putInt("id", learnItem.id)
//                val newFragment = LearnItemFragment.getFragmentInstance(bun)
//                launchFragment(newFragment)
//            }
//        }
//        adapter.onLearnItemLongClickListener = object : MyAdapter.OnLearnItemLongClickListener{
//            override fun onLearnItemLongClicked(learnItem: ItemLearn):Boolean {
//                val bun = Bundle()
//                bun.putInt("id", learnItem.id)
//                launchFragment(EditLearnItemFragment.getFragmentInstance(bun))
//                return true
//            }
//
//        }
//        recView.adapter = adapter

    }

    private fun setUpClickListeners() {
        with(binding) {

            buttonAddShopItem.setOnClickListener {
                val bun = Bundle()
                bun.putInt("id", 0)
                launchFragment(EditLearnItemFragment.getFragmentInstance(bun))
            }

            button.setOnClickListener {
                GlobalScope.launch {
//                LearnRepositoryImpl.getInstance(application).updateFromApi()
                }
                //vm.fillArr()
//            vm.testRep()
            }
        }
    }

    private fun launchFragment(fragment: Fragment) {
        this@MainFragment.parentFragmentManager.beginTransaction().addToBackStack("test")
            .replace(
                R.id.container, fragment
            ).commit()
    }

    private fun onClick(): (LearnItemDataClass) -> Unit = {
        Log.d("yarek", "clicked "+it.description)
    }

    private fun initRecyclerView() {
        with(binding) {
            communitiesAdapter = MyAdapter(onClick())
            binding.recyclerView.adapter = communitiesAdapter
        }
    }

    fun observeAllWords() {
        viewModel.allInfoData.observe(viewLifecycleOwner) { data ->
            Log.d("mylog"," data "+data?.size)
          //  communitiesAdapter.  //використати  submitlist для передачі данних
        }


    //        vm.dataToRecycl.observe(viewLifecycleOwner) {
//            adapter.updateList(it)
//        }

//            vm.dataToRecyclStateFlow.collectLatest {
//                println("inside mainFragment obsorver of StateFlow 992345678987654323456789098765432123456789098765432")
//                Log.d("test1", "inside mainFragment obsorver of StateFlow 992345678987654323456789098765432123456789098765432")
//
//                    //                adapter.updateList(it)
//
//            }
    }

}