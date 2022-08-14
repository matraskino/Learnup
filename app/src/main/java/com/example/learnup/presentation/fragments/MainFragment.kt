package com.example.learnup.presentation.fragments

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnup.R
import com.example.learnup.data.repositories.LearnRepositoryImpl
import com.example.learnup.databinding.MainFragmentBinding
import com.example.learnup.di.AppModule
import com.example.learnup.di.DaggerApplicationComponent
import com.example.learnup.domain.models.ItemLearn
import com.example.learnup.presentation.App
import com.example.learnup.presentation.vmFactories.MainViewModelFactory
import com.example.learnup.presentation.MyAdapter
import com.example.learnup.presentation.viewModels.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainFragment : Fragment() {

    lateinit var binding: MainFragmentBinding

    lateinit var vm: MainViewModel
    private lateinit var application:Application

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    lateinit var adapter: MyAdapter


    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        application = requireActivity().application
        (application as App).applicationComponent.inject(this)

        vm = ViewModelProvider(
            this,
            mainViewModelFactory
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
                bun.putString("definition", learnItem.description)
                bun.putString("extraDescription", learnItem.extraDescription)
                bun.putInt("id", learnItem.id)
                val newFragment = LearnItemFragment.getFragmentInstance(bun)
                launchFragment(newFragment)
            }
        }
        adapter.onLearnItemLongClickListener = object : MyAdapter.OnLearnItemLongClickListener {
            override fun onLearnItemLongClicked(learnItem: ItemLearn):Boolean {

                return true
            }

        }
        recView.adapter = adapter
        binding.button.setOnClickListener {
            GlobalScope.launch {

                LearnRepositoryImpl.getInstance(application).updateFromApi()
            }
            //vm.fillArr()
//            vm.testRep()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonAddItem = binding.buttonAddShopItem
        vm.observSettings()
        buttonAddItem.setOnClickListener {
            val bun = Bundle()
            bun.putInt("id", 0)
            launchFragment(EditLearnItemFragment.getFragmentInstance(bun))
        }

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }
    private fun launchFragment(fragment:Fragment){
        this@MainFragment.parentFragmentManager.beginTransaction().addToBackStack("test")
            .replace(
                R.id.container, fragment
            ).commit()
    }

    fun observeAllWords() {
        Log.d("test1", "obsorver started at MainFragment")
        vm.dataToRecycl.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }
    }
}