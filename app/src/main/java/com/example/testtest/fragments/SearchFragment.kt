package com.example.testtest.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.testtest.MainActivity
import com.example.testtest.MainViewModel
import com.example.testtest.MainViewModelFactory
import com.example.testtest.R
import com.example.testtest.databinding.FragmentSearchBinding
import com.example.testtest.repository.Repository
private lateinit var binding: FragmentSearchBinding
private lateinit var viewModel: MainViewModel
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"




class SearchFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var userInput = ""
        val handler = Handler(Looper.getMainLooper())
        val searchRunnable = Runnable{getCoctailsByName(userInput)}


        val searchView = binding.SV
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if(p0 != null){
                    userInput = p0.toString()
                    getCoctailsByName(userInput)

                }
            return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                userInput = text.toString()
                handler.removeCallbacks(searchRunnable)
                handler.postDelayed(searchRunnable, 300L)
                return true
            }


        })}

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun getCoctailsByName(name: String){
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getCocktailsByName(name)
        viewModel.fullResponse.observe(viewLifecycleOwner, Observer { response ->
            if(response.body()!!.fullRes != null){
                for (c in response.body()!!.fullRes){
                    Log.d("RETURN", c.strDrink)


                }
            }
        })

    }

    }


