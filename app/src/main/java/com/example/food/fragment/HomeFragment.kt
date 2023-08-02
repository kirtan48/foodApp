package com.example.food.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.food.Activities.CategoryMealsActivity
import com.example.food.Activities.MealActivity
import com.example.food.adapters.CategoriesAdapter
import com.example.food.adapters.MostPopularAdapter
import com.example.food.databinding.FragmentHomeBinding
import com.example.food.pojo.MealsByCategory
import com.example.food.pojo.Meal
import com.example.food.videoModel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeMvvm:HomeViewModel
    private lateinit var randomMeal:Meal
    private lateinit var poplarItemAdapter:MostPopularAdapter
    private lateinit var categoriesAdapter:CategoriesAdapter

    companion object{

        const val MEAL_ID="com.example.food.fragment.idMeal"
        const val MEAL_NAME="com.example.food.fragment.nameMeal"
        const val MEAL_THUMB="com.example.food.fragment.thumbMeal"
        const val CATEGORY_NAME="com.example.food.fragment.categoryName"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //homeMvvm= ViewModelProviders.of(this,)[HomeViewModel::class.java]
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
        poplarItemAdapter= MostPopularAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()
        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMEalClick()
        homeMvvm.getPopularItems()
        observerPopularItemsLiveData()
        prepareCategoriesRecyclerView()
        homeMvvm.getCategories()
        observerCategoriesLiveData()
        onCategoryClick()


    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick={category->
            val intent=Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter= CategoriesAdapter()
        binding.recycleCategory.apply{
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoriesAdapter
        }
    }

    private fun observerCategoriesLiveData() {
      homeMvvm.observeCategoryLiveData().observe(viewLifecycleOwner) { categories ->
          categories.forEach {
              categoriesAdapter.setCategoryList(categories)
          }

      }
    }

    private fun preparePopularItemsRecyclerView() {
       binding.recycleViewMeal.apply {
           layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
           adapter=poplarItemAdapter
       }
    }

    private fun observerPopularItemsLiveData() {
        homeMvvm.observePopularItemLiveData().observe(viewLifecycleOwner) {
                mealsList ->

            poplarItemAdapter.setMeals(mealList = mealsList as ArrayList<MealsByCategory>)

        }
    }

    private fun onRandomMEalClick() {
       binding.randomMealCard.setOnClickListener{
           val intent= Intent(activity,MealActivity::class.java)
           intent.putExtra(MEAL_ID,randomMeal.idMeal)
           intent.putExtra(MEAL_NAME,randomMeal.strMeal)
           intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
           startActivity(intent)
       }
    }

    /*
     private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLivedata().observe(viewLifecycleOwner,object :Observer<Meal>{
            override fun onChanged(t: Meal?) {
                Glide.with(this@HomeFragment)
                    .load(t!!.strMealThumb)
                    .into(binding.imgRandm)
            }
        })
    }*/


    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLivedata().observe(viewLifecycleOwner) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandm)

            this.randomMeal=meal

        }
    }

}


