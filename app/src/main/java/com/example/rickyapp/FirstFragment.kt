package com.example.rickyapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickyapp.characterlist.CharacterAdapter
import com.example.rickyapp.characterlist.CharacterListViewModel
import com.example.rickyapp.databinding.FragmentFirstBinding
import com.example.rickyapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val viewModel: CharacterListViewModel by viewModels()

    private lateinit var adapter: CharacterAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startObservers()
        adapter = CharacterAdapter()
        _binding?.recyclerView?.adapter = adapter
        _binding?.recyclerView?.layoutManager = LinearLayoutManager(activity)

        // this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun startObservers(){
        viewModel.repository.observe(viewLifecycleOwner){
            when(it.status){
                Resource.Status.SUCCESS ->{ //200
                    // pass the data to recyclerview
                    Log.i("Data", ""+ (it.data))
                    adapter.submitList(it.data)

                    // create a recyclerview adapter
                }
                Resource.Status.ERROR ->{
                    Log.i("Error", it.message.toString())
                }
                Resource.Status.LOADING -> {
                    // Progress Dialog
                }
            }
        }
    }
}