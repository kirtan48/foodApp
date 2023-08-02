package com.example.food.videoModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food.RetroFit.RetroftInstance
import com.example.food.pojo.MealsByCategory
import com.example.food.pojo.MealsByCategoryList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealViewModel(): ViewModel() {
    val mealsLiveData=MutableLiveData<List<MealsByCategory>>()
    fun getMealsByCategory(categoryName:String){
         RetroftInstance.api.getMealsByCategory(categoryName).enqueue(object: Callback<MealsByCategoryList>{
             override fun onResponse(
                 call: Call<MealsByCategoryList>,
                 response: Response<MealsByCategoryList>
             ) {
                 response.body()?.let {mealsList->
                     mealsLiveData.postValue(mealsList.meals)

                 }
             }

             override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                 Log.d("CategoryMealsViewModel",t.message.toString())

             }

         })
    }
    fun observeMealsLiveData(): LiveData<List<MealsByCategory>> {
        return mealsLiveData
    }


}