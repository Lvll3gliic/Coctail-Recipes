package com.example.testtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testtest.databinding.ActivityMainBinding
import com.example.testtest.fragments.*
import com.example.testtest.model.Drinks
import com.example.testtest.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
const val BASE_URL = "https://www.thecocktaildb.com/"
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.categories -> replaceFragment(CategoryFragment())
                R.id.home -> replaceFragment(HomeFragment())
                R.id.favorites -> replaceFragment(FavoritesFragment())
                R.id.search -> replaceFragment(SearchFragment())
            }
        true
        }



    }
    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()

    }

}

