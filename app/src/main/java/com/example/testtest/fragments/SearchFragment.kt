package com.example.testtest.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testtest.*
import com.example.testtest.adapter.MyAdapter
import com.example.testtest.databinding.FragmentSearchBinding
import com.example.testtest.repository.Repository
import com.example.testtest.adapter.MyAdapter.OnItemClickListener as OnItemClickListener1

private lateinit var binding: FragmentSearchBinding
private lateinit var viewModel: MainViewModel
lateinit var myAdapter: MyAdapter
lateinit var linearLayoutManager: LinearLayoutManager
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
        linearLayoutManager = LinearLayoutManager(activity)
        val searchRecycler = binding.searchRecycler
        linearLayoutManager = LinearLayoutManager(activity)
        searchRecycler.layoutManager = linearLayoutManager

        var userInput = ""
        val handler = Handler(Looper.getMainLooper())
        val searchRunnable = Runnable{getCoctailsByName(userInput,searchRecycler )}


        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if(p0 != null){
                    userInput = p0.toString()
                    getCoctailsByName(userInput, searchRecycler)

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
    fun getCoctailsByName(name: String, view : RecyclerView){
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getCocktailsByName(name)
        viewModel.fullResponse.observe(viewLifecycleOwner, Observer { response ->
            if(response.body()!!.fullRes != null){
                myAdapter = MyAdapter(activity, response.body())
                myAdapter.notifyDataSetChanged()
                view.adapter = myAdapter
                myAdapter.setOnItemClickListener(object:MyAdapter.OnItemClickListener{
                    override fun onItemClick(position: Int) {
                        //to use fragments in transaction we need to make val, value is
                        //fragment name with ()
                        val recipefragment = RecipeFragment()
                        //needs to make a bundle to send data from one fragment to another
                        val bundle = Bundle()
                        //put string in ("variable name", value)
                        bundle.putString("drinkId",response.body()!!.fullRes[position].idDrink)
                        //then transaction from this fragment to recipe fragment
                        val transaction = fragmentManager?.beginTransaction()
                        //add bundle as argument
                        recipefragment.arguments = bundle
                        // to open new fragment we need to replace frame layout from
                        //main activity to this fragment (it always happens when changing fragments)
                        //this should work from any fragment
                        transaction?.replace(R.id.frame_layout, recipefragment)
                        transaction?.commit()
                    }
                })
            }
        })

    }

}




