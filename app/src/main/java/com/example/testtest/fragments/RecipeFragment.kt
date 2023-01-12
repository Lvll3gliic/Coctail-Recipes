package com.example.testtest.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.testtest.MainViewModel
import com.example.testtest.MainViewModelFactory
import com.example.testtest.R
import com.example.testtest.databinding.FragmentRecipeBinding
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

        //this just takpes argument from revious fragment - this time drink ID so that we can search drink by ID
        val drinkId = arguments?.getString("drinkId")
        getDrinkInfoById(drinkId!!.toInt())
        Log.d("WWWW", drinkId);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindings = FragmentRecipeBinding.inflate(layoutInflater)


        // This is where I edit my layouts to match the design

        val drinkId = arguments?.getString("drinkId")
        //bindings.textView.text = drinkId;









        // ends here

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

            Log.d("RESPONSE", response.body()!!.fullRes[0].idDrink);

            bindings.name.text = response.body()!!.fullRes[0].strDrink;

            bindings.ing1.text = response.body()!!.fullRes[0].strIngredient1;

            if(response.body()!!.fullRes[0].strIngredient2 != null){
                val textView = TextView(activity)
                textView.setText(response.body()!!.fullRes[0].strIngredient2)
                bindings.scrollContainer.addView(textView);
            }
            if(response.body()!!.fullRes[0].strIngredient3 != null){
                val textView = TextView(activity)
                textView.setText(response.body()!!.fullRes[0].strIngredient3)
                bindings.scrollContainer.addView(textView);
            }
            if(response.body()!!.fullRes[0].strIngredient4 != null){
                val textView = TextView(activity)
                textView.setText(response.body()!!.fullRes[0].strIngredient4)
                bindings.scrollContainer.addView(textView);
            }
            if(response.body()!!.fullRes[0].strIngredient5 != null){
                val textView = TextView(activity)
                textView.setText(response.body()!!.fullRes[0].strIngredient5)
                bindings.scrollContainer.addView(textView);
            }
            if(response.body()!!.fullRes[0].strIngredient6 != null){
                val textView = TextView(activity)
                textView.setText(response.body()!!.fullRes[0].strIngredient6)
                bindings.scrollContainer.addView(textView);
            }
            if(response.body()!!.fullRes[0].strIngredient7 != null){
                val textView = TextView(activity)
                textView.setText(response.body()!!.fullRes[0].strIngredient7)
                bindings.scrollContainer.addView(textView);
            }
            if(response.body()!!.fullRes[0].strIngredient8 != null){
                val textView = TextView(activity)
                textView.setText(response.body()!!.fullRes[0].strIngredient8)
                bindings.scrollContainer.addView(textView);
            }
            if(response.body()!!.fullRes[0].strIngredient9 != null){
                val textView = TextView(activity)
                textView.setText(response.body()!!.fullRes[0].strIngredient9)
                bindings.scrollContainer.addView(textView);
            }
            if(response.body()!!.fullRes[0].strIngredient10 != null){
                val textView = TextView(activity)
                textView.setText(response.body()!!.fullRes[0].strIngredient10)
                bindings.scrollContainer.addView(textView);
            }
            if(response.body()!!.fullRes[0].strIngredient11 != null){
                val textView = TextView(activity)
                textView.setText(response.body()!!.fullRes[0].strIngredient11)
                bindings.scrollContainer.addView(textView);
            }
            if(response.body()!!.fullRes[0].strIngredient12 != null){
                val textView = TextView(activity)
                textView.setText(response.body()!!.fullRes[0].strIngredient12)
                bindings.scrollContainer.addView(textView);
            }
            if(response.body()!!.fullRes[0].strIngredient13 != null){
                val textView = TextView(activity)
                textView.setText(response.body()!!.fullRes[0].strIngredient13)
                bindings.scrollContainer.addView(textView);
            }
            if(response.body()!!.fullRes[0].strIngredient14 != null){
                val textView = TextView(activity)
                textView.setText(response.body()!!.fullRes[0].strIngredient14)
                bindings.scrollContainer.addView(textView);
            }
            if(response.body()!!.fullRes[0].strIngredient15 != null){
                val textView = TextView(activity)
                textView.setText(response.body()!!.fullRes[0].strIngredient15)
                bindings.scrollContainer.addView(textView);
            }

 
            bindings.inststr.text = response.body()!!.fullRes[0].strInstructions;
            //here should be placed code for displaying all the information

        })

    }
}