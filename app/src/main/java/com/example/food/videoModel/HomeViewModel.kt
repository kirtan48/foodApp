package com.example.food.videoModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food.RetroFit.RetroftInstance
import com.example.food.pojo.Category
import com.example.food.pojo.CategoryList
import com.example.food.pojo.MealsByCategoryList
import com.example.food.pojo.MealsByCategory
import com.example.food.pojo.Meal
import com.example.food.pojo.MealList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(): ViewModel() {
    private var randomMealLiveData= MutableLiveData<Meal>()
    private var popularItemsLiveData=MutableLiveData<List<MealsByCategory>>()
    private var categoriesLivedata=MutableLiveData<List<Category>>()
    fun getRandomMeal(){
        RetroftInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null){
                    val randomMeal: Meal =response.body()!!.meals[0]
                    randomMealLiveData.value=randomMeal
                    Log.d("test","meal id ${randomMeal.idMeal} name ${randomMeal.strMeal}")
                }
                else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home fragment",t.message.toString())
            }
        })
    }
    fun getPopularItems(){
        RetroftInstance.api.getPopularItems("Seafood").enqueue(object :Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if(response.body()!=null){
                    popularItemsLiveData.value=response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("Home fragment",t.message.toString())
            }

        })
    }
    fun getCategories(){
        RetroftInstance.api.getCategories().enqueue(object :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { categoryList ->
                    categoriesLivedata.postValue(categoryList.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("Home fragment",t.message.toString())
            }

        })
    }

    fun observeRandomMealLivedata():LiveData<Meal>{
        return randomMealLiveData
    }
    fun observePopularItemLiveData():LiveData<List<MealsByCategory>>{
        return popularItemsLiveData
    }
    fun observeCategoryLiveData():LiveData<List<Category>>{
        return categoriesLivedata
    }
}