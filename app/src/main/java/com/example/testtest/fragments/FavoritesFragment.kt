package com.example.testtest.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testtest.MainViewModel
import com.example.testtest.MainViewModelFactory
import com.example.testtest.R
import com.example.testtest.adapter.MyAdapter
import com.example.testtest.databinding.FragmentFavoritesBinding
import com.example.testtest.repository.Repository


lateinit var myAdapter_fav: MyAdapter
lateinit var linearLayoutManager_fav: LinearLayoutManager

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
lateinit var binding_fav: FragmentFavoritesBinding
private lateinit var viewModel: MainViewModel
class FavoritesFragment : Fragment() {

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
        binding_fav = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding_fav.root




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val favoriteRecycle = binding_fav.favoriteRecycler

        linearLayoutManager_fav = LinearLayoutManager(activity)
        favoriteRecycle.layoutManager = linearLayoutManager_fav
        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val name = preferences.getString("id", "")

            if(!name.equals("")){
                if (name != null) {
                    getCoctailsByName(name.toInt(),favoriteRecycle)
                }
            }else{
                binding_fav.clearfav.visibility = View.GONE
            }

        binding_fav.clearfav.setOnClickListener(View.OnClickListener {
            val preferences = PreferenceManager.getDefaultSharedPreferences(
                activity
            )
            val editor = preferences.edit()
            editor.putString("id", "");
            editor.apply()

            val favFragment = FavoritesFragment()
            val transaction = fragmentManager?.beginTransaction()

            transaction?.replace(R.id.frame_layout, favFragment)
            transaction?.commit()


        })
    }


    fun getCoctailsByName(id: Int, view : RecyclerView){
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getFullInfoById(id)
        viewModel.fullResponse.observe(viewLifecycleOwner, Observer { response ->
            if(response.body()!!.fullRes != null){
                myAdapter_fav = MyAdapter(activity, response.body())
                myAdapter_fav.notifyDataSetChanged()
                view.adapter = myAdapter_fav
                myAdapter_fav.setOnItemClickListener(object: MyAdapter.OnItemClickListener{
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
            }else{
                //here needs to be some text that didn't find any cocktails
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoritesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }


            }
    }
}


