package com.example.snacc

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.snacc.adapter.FoodAdapter
import com.example.snacc.model.Food
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {

    private lateinit var rvFoods: RecyclerView
    private lateinit var btnLayoutToggle: ImageButton
    private lateinit var bottomNav: BottomNavigationView

    private var isGridLayout = true
    private val foodList = mutableListOf<Food>()
    private lateinit var adapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // --- Inisialisasi View
        rvFoods = findViewById(R.id.rvFoods)
        btnLayoutToggle = findViewById(R.id.btnLayoutToggle)
        bottomNav = findViewById(R.id.bottomNav)

        // --- Dummy data makanan
        foodList.addAll(
            listOf(
                Food("Burger", "Roti isi daging sapi dan sayur segar.", R.drawable.food1),
                Food("French Fries", "Kentang goreng gurih renyah.", R.drawable.food2),
                Food("Potato Chips", "Keripik kentang asin yang bikin nagih.", R.drawable.food3),
                Food("Hot Dog", "Sosis besar dalam roti lembut dengan saus spesial.", R.drawable.food4),
                Food("Fried Chicken", "Ayam goreng krispi dengan bumbu rahasia.", R.drawable.food5),
                Food("Pizza Slice", "Potongan pizza keju meleleh dengan topping favorit.", R.drawable.food6),
                Food("Donut", "Donat manis lembut dengan taburan gula halus.", R.drawable.food7),
                Food("Ice Cream Cone", "Es krim lembut dalam cone renyah.", R.drawable.food8),
                Food("Sandwich", "Roti lapis isi daging, keju, dan sayur segar.", R.drawable.food9),
                Food("Milkshake", "Minuman manis kental rasa vanilla & cokelat.", R.drawable.food10)
            )
        )

        // --- Setup awal RecyclerView
        setupRecyclerView()

        // --- Toggle Grid/List
        btnLayoutToggle.setOnClickListener {
            toggleLayout()
        }

        // --- Bottom Navigation Listener
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Kamu di Beranda 🍔", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_cart -> {
                    Toast.makeText(this, "Keranjang kamu 🛒", Toast.LENGTH_SHORT).show()
                    // startActivity(Intent(this, CartActivity::class.java))
                    true
                }

                R.id.nav_profile -> {
                    Toast.makeText(this, "Lihat Profil 👤", Toast.LENGTH_SHORT).show()
                    // startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }

                else -> false
            }
        }
    }

    // --- Setup RecyclerView
    private fun setupRecyclerView() {
        adapter = FoodAdapter(
            foodList,
            onAddToCart = { selectedFood ->
                Toast.makeText(this, "${selectedFood.name} masuk ke keranjang kamu! 🛒", Toast.LENGTH_SHORT).show()
            },
            onOrderNow = { selectedFood ->
                Toast.makeText(this, "Pesan ${selectedFood.name} sekarang 🍽️", Toast.LENGTH_SHORT).show()

                // Intent ke OrderActivity
                val intent = Intent(this, OrderActivity::class.java)
                intent.putExtra("FOOD_NAME", selectedFood.name)
                startActivity(intent)
            }
        )

        rvFoods.adapter = adapter
        rvFoods.layoutManager = GridLayoutManager(this, 2)
        rvFoods.setHasFixedSize(true)
    }

    private fun toggleLayout() {
        isGridLayout = !isGridLayout
        rvFoods.layoutManager =
            if (isGridLayout) GridLayoutManager(this, 2)
            else LinearLayoutManager(this)
        adapter.notifyItemRangeChanged(0, foodList.size)

        btnLayoutToggle.setImageResource(
            if (isGridLayout) R.drawable.baseline_clear_all_24
            else R.drawable.baseline_clear_all_24
        )
    }
}
