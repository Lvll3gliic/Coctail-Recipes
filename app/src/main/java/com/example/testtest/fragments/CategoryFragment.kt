package com.example.testtest.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testtest.MainViewModel
import com.example.testtest.MainViewModelFactory
import com.example.testtest.R
import com.example.testtest.adapter.MyAdapter
import com.example.testtest.adapter.MyAdapterCategory
import com.example.testtest.databinding.FragmentCategoryBinding
import com.example.testtest.repository.Repository


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel: MainViewModel
    lateinit var bindingCategory: FragmentCategoryBinding
    lateinit var myAdapter: MyAdapterCategory
    lateinit var linearLayoutManagerCategory: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        linearLayoutManagerCategory = LinearLayoutManager(activity)
        val categoryView = bindingCategory.categoryRecycler
        categoryView.layoutManager = linearLayoutManagerCategory
        getCategories(categoryView)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingCategory = FragmentCategoryBinding.inflate(layoutInflater)

        return bindingCategory.root
    }


    fun getCategories(view: RecyclerView){
        //This is for API call to get all the categories that are used in this API
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getPost()
        viewModel.myResponse.observe(viewLifecycleOwner, Observer{response ->
            Log.d("responsee", response.body()!!.res.get(0).category)
            if(response.body()!!.res != null){
                myAdapter = MyAdapterCategory(activity, response)

                myAdapter.notifyDataSetChanged()
                view.adapter = myAdapter
                myAdapter.setOnItemClickListener(object:MyAdapterCategory.OnItemClickListener{
                    override fun onItemClick(position: Int) {
                        //to use fragments in transaction we need to make val, value is
                        //fragment name with ()
                        val drinksByCategory = DrinksByCategoryFragment()
                        //needs to make a bundle to send data from one fragment to another
                        val bundle = Bundle()
                        //put string in ("variable name", value)
                        bundle.putString("category",response.body()!!.res.get(position).category)
                        //then transaction from this fragment to recipe fragment
                        val transaction = fragmentManager?.beginTransaction()
                        //add bundle as argument
                        drinksByCategory.arguments = bundle
                        // to open new fragment we need to replace frame layout from
                        //main activity to this fragment (it always happens when changing fragments)
                        //this should work from any fragment
                        transaction?.replace(R.id.frame_layout, drinksByCategory)
                        transaction?.commit()
                    }
                })
            }
        })
    }


    internal object test{
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}