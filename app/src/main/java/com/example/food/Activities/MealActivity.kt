package com.example.food.Activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.food.R
import com.example.food.databinding.ActivityMealBinding
import com.example.food.fragment.HomeFragment
import com.example.food.pojo.Meal
import com.example.food.videoModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var mealid:String
    private lateinit var mealname:String
    private lateinit var mealthumb:String
    private lateinit var binding:ActivityMealBinding
    private lateinit var mealMvvm:MealViewModel
    private lateinit var youtubeLink:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealBinding.inflate(layoutInflater)

        setContentView(binding.root)
        getMealINfromationFromINtent()
        setInformationOnVies()
        loadingcase()

        mealMvvm= ViewModelProviders.of(this)[MealViewModel::class.java]
        mealMvvm.getMealDetail(mealid)
        observerMealDetailsLiveData()
        onYoutube()

    }
    private fun observerMealDetailsLiveData(){
        mealMvvm.observerMealDetailsLiveData().observe(this,object : Observer<Meal>{
            override fun onChanged(t: Meal?) {
                responsecase()


                val meal=t

                binding.tvCategory.text="Category : ${meal!!.strCategory}"
                binding.tvArea.text="Area : ${meal!!.strArea}"
                binding.tvInstructionContent.text=meal.strInstructions
                youtubeLink=meal.strYoutube

            }

        })

    }

    private fun setInformationOnVies() {
       Glide.with(applicationContext)
           .load(mealthumb)
           .into(binding.imgImageDetail)
        binding.collapsinToolbar.title=mealname
        binding.collapsinToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsinToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealINfromationFromINtent() {
        val intent=intent
        mealid=intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealname=intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealthumb=intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }
    private fun loadingcase(){
        binding.tvArea.visibility= View.INVISIBLE
        binding.tvCategory.visibility= View.INVISIBLE
        binding.tvInstructionContent.visibility= View.INVISIBLE
        binding.tvInstruction.visibility= View.INVISIBLE
        binding.btnFav.visibility= View.INVISIBLE
        binding.progreeeBar.visibility= View.VISIBLE


    }
    private fun responsecase(){
        binding.tvArea.visibility= View.VISIBLE
        binding.tvCategory.visibility= View.VISIBLE
        binding.tvInstructionContent.visibility= View.VISIBLE
        binding.tvInstruction.visibility= View.VISIBLE
        binding.btnFav.visibility= View.VISIBLE
        binding.progreeeBar.visibility= View.INVISIBLE

    }
    private fun onYoutube(){
        binding.imgYt.setOnClickListener{
            val intent= Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }
}