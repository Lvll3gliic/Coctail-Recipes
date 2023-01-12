package com.example.testtest.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.testtest.MainViewModel
import com.example.testtest.MainViewModelFactory
import com.example.testtest.R
import com.example.testtest.databinding.FragmentHomeBinding
import com.example.testtest.repository.Repository

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var viewModel: MainViewModel
lateinit var bindings1: FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        //this gets random drink for the home/random page
        //This already gets all the information about recipe
      getRandomCocktail()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindings1 = FragmentHomeBinding.inflate(layoutInflater)
        return bindings1.root

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun getRandomCocktail(){
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getRandomDrink()
        viewModel.fullResponse.observe(this, Observer{response ->
            if(response.isSuccessful){
                //I chose to use fullRes[0] - array first element because it only gives one recipe
                //so this works this time without any problems
                Log.d("RESPONSE", response.body()!!.fullRes[0].strDrink)
                bindings1.randname.text = response.body()!!.fullRes[0].strDrink
                bindings1.randingr1.text = response.body()!!.fullRes[0].strIngredient1
                bindings1.randingr2.text = response.body()!!.fullRes[0].strIngredient2
                bindings1.randingr3.text = response.body()!!.fullRes[0].strIngredient3
                Glide.with(this).load(response.body()!!.fullRes[0].strDrinkThumb).into(bindings1.randpic)


                bindings1.randyes.setOnClickListener(View.OnClickListener {
                    //to use fragments in transaction we need to make val, value is
                    //fragment name with ()
                    val recipefragment = RecipeFragment()
                    //needs to make a bundle to send data from one fragment to another
                    val bundle = Bundle()
                    //put string in ("variable name", value)
                    bundle.putString("drinkId",response.body()!!.fullRes[0].idDrink)
                    //then transaction from this fragment to recipe fragment
                    val transaction = fragmentManager?.beginTransaction()
                    //add bundle as argument
                    recipefragment.arguments = bundle
                    // to open new fragment we need to replace frame layout from
                    //main activity to this fragment (it always happens when changing fragments)
                    //this should work from any fragment
                    transaction?.replace(R.id.frame_layout, recipefragment)
                    transaction?.commit()
                })

                bindings1.randno.setOnClickListener(View.OnClickListener {
                    viewModel.getRandomDrink()
                })

            }else{
                Log.d("ERROR", response.code().toString())
            }
        })
    }
}