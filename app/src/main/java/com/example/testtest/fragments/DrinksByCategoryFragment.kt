package com.example.testtest.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testtest.MainViewModel
import com.example.testtest.MainViewModelFactory
import com.example.testtest.R
import com.example.testtest.adapter.MyAdapter
import com.example.testtest.databinding.FragmentDrinksByCategoryBinding
import com.example.testtest.databinding.FragmentSearchBinding
import com.example.testtest.repository.Repository

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var viewModel: MainViewModel
lateinit var bindingDrinksCategory: FragmentDrinksByCategoryBinding
lateinit var drinksAdapter: MyAdapter


class DrinksByCategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
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
        bindingDrinksCategory = FragmentDrinksByCategoryBinding.inflate(layoutInflater)
        return bindingDrinksCategory.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        linearLayoutManager = LinearLayoutManager(activity)
        val drinksRecycler = bindingDrinksCategory.drinksRecycler
        linearLayoutManager = LinearLayoutManager(activity)
        drinksRecycler.layoutManager = linearLayoutManager
        val category = arguments?.getString("category")
        getCocktailsByCategory(category.toString(),drinksRecycler )
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DrinksByCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun getCocktailsByCategory(category: String, view: RecyclerView){
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getCocktailsByCategory(category)
        viewModel.fullResponse.observe(requireActivity(), Observer { response ->

                drinksAdapter = MyAdapter(activity, response.body())
                drinksAdapter.notifyDataSetChanged()
                view.adapter = drinksAdapter
                drinksAdapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {





                        //to use fragments in transaction we need to make val, value is
                        //fragment name with ()
                        //val recipefragment = RecipeFragment()
                        //needs to make a bundle to send data from one fragment to another
                        //val bundle = Bundle()
                        //put string in ("variable name", value)
                       // bundle.putString("drinkId", response.body()!!.fullRes[position].idDrink)
                        //then transaction from this fragment to recipe fragment
                      //  val transaction = fragmentManager?.beginTransaction()
                        //add bundle as argument
                       // recipefragment.arguments = bundle
                        // to open new fragment we need to replace frame layout from
                        //main activity to this fragment (it always happens when changing fragments)
                        //this should work from any fragment
                       // transaction?.replace(R.id.frame_layout, recipefragment)
                       // transaction?.commit()
                    }


                })

        })
    }
}