package com.example.testtest.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtest.MainViewModel
import com.example.testtest.MainViewModelFactory
import com.example.testtest.R
import com.example.testtest.adapter.MyAdapter
import com.example.testtest.databinding.FragmentRecipeBinding
import com.example.testtest.databinding.FragmentSearchBinding
import com.example.testtest.repository.Repository

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var viewModel: MainViewModel
lateinit var bindings: FragmentRecipeBinding



class RecipeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //all this code for back button to work properly
        activity?.onBackPressedDispatcher?.addCallback(this, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                    val transaction = fragmentManager?.beginTransaction()
                    val searchFragment = SearchFragment()
                    transaction?.replace(R.id.frame_layout, searchFragment )
                    transaction?.commit()

            }
        })
        //ends here, just change the val searchFragment value to which u need

        //this just takes argument from previous fragment - this time drink ID so that we can search drink by ID
        val drinkId = arguments?.getString("drinkId")
        getDrinkInfoById(drinkId!!.toInt())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindings = FragmentRecipeBinding.inflate(layoutInflater)
        return bindings.root


        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            RecipeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    //function to get drink by id
    fun getDrinkInfoById(id:Int){
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getFullInfoById(id)
        viewModel.fullResponse.observe(requireActivity(), Observer{ response ->

           //here should be placed code for displaying all the information

        })
    }
}