package com.example.food.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.food.R
import com.example.food.adapters.CategoryMealsAdapter
import com.example.food.databinding.ActivityCategoryMealsBinding
import com.example.food.fragment.HomeFragment
import com.example.food.videoModel.CategoryMealViewModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding:ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel:CategoryMealViewModel
    lateinit var categoryMealsAdapter:CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()
        Toast.makeText(this, "reached", Toast.LENGTH_SHORT).show()

        categoryMealsViewModel=ViewModelProviders.of(this)[CategoryMealViewModel::class.java]

        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer{mealsList->
            binding.tvCategoryCount.text=mealsList.size.toString()
            categoryMealsAdapter.setMealList(mealsList)
            Log.d("test",mealsList.toString())

        })

    }

    private fun prepareRecyclerView() {
       categoryMealsAdapter= CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
        adapter=categoryMealsAdapter
        }
    }

}