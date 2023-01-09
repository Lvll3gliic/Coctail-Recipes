package com.example.testtest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtest.model.Data
import com.example.testtest.model.FullData
import com.example.testtest.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<Response<Data>> = MutableLiveData()
    val fullResponse: MutableLiveData<Response<FullData>> = MutableLiveData()
    fun getPost(){
        viewModelScope.launch{
            val response: Response<Data> = repository.getPost()
            myResponse.value = response
        }
    }

    fun getFullInfoById(id: Int){
        viewModelScope.launch {
            val response: Response<FullData> = repository.getFullInfoById(id)
            fullResponse.value = response
        }
    }
    fun getRandomDrink(){
        viewModelScope.launch {
            val response: Response<FullData> = repository.getRandomDrink()
            fullResponse.value = response
        }
    }
    fun getCocktailsByName(name:String){
        viewModelScope.launch {
            val response: Response<FullData> = repository.getCocktailsByName(name)
            fullResponse.value = response
        }
    }
    fun getCocktailsByCategory(category: String){
        viewModelScope.launch {
            val response : Response<FullData> = repository.getCocktailsByCategory(category)
            fullResponse.value = response
        }
    }
}