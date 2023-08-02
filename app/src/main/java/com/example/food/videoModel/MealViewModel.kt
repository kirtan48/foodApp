package com.example.food.videoModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food.RetroFit.RetroftInstance
import com.example.food.pojo.Meal
import com.example.food.pojo.MealList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class MealViewModel():ViewModel() {
    private var mealDetailLiveData= MutableLiveData<Meal>()
    fun getMealDetail(id:String){
        RetroftInstance.api.getMealDetail(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response:Response<MealList>){
                if(response.body()!=null){
                    mealDetailLiveData.value=response.body()!!.meals[0]
                }
                else{
                    return
                }

            }
            override fun onFailure(call:Call<MealList>,t:Throwable){
                Log.d("MealActivity",t.message.toString())


            }

        })


    }
    fun observerMealDetailsLiveData(): LiveData<Meal> {
        return mealDetailLiveData
    }

}