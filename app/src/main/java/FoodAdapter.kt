package com.example.snacc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.example.snacc.R
import com.example.snacc.model.Food

class FoodAdapter(
    private val foodList: List<Food>,
    private val onAddToCart: (Food) -> Unit,
    private val onOrderNow: (Food) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgFood: ImageView = itemView.findViewById(R.id.imgFood)
        val tvName: TextView = itemView.findViewById(R.id.tvFoodName)
        val tvDesc: TextView = itemView.findViewById(R.id.tvFoodDesc)
        val btnAddToCart: MaterialButton = itemView.findViewById(R.id.btnAddToCart)
        val btnOrderNow: MaterialButton = itemView.findViewById(R.id.btnOrderNow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]

        holder.apply {
            tvName.text = food.name
            tvDesc.text = food.description
            imgFood.setImageResource(food.imageResId)

            btnAddToCart.setOnClickListener { onAddToCart(food) }
            btnOrderNow.setOnClickListener { onOrderNow(food) }
        }
    }

    override fun getItemCount() = foodList.size
}
