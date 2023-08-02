package com.example.food.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food.databinding.MealItemBinding
import com.example.food.pojo.MealsByCategory

class CategoryMealsAdapter:RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealViewModel>() {

    private var mealList=ArrayList<MealsByCategory>()
    fun setMealList(mealList: List<MealsByCategory>){
        this.mealList=mealList as ArrayList<MealsByCategory>
        Log.d("test","reach"+mealList.toString())
        notifyDataSetChanged()
    }
    inner class CategoryMealViewModel(val binding:MealItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealViewModel {
        return CategoryMealViewModel(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return mealList.size

    }

    override fun onBindViewHolder(holder: CategoryMealViewModel, position: Int) {
        Log.d("test1",mealList[position].strMealThumb)
        Glide.with(holder.itemView).load(mealList[position].strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMealName.text=mealList[position].strMeal
        Log.d("test1", holder.binding.tvMealName.text.toString() )
    }
}