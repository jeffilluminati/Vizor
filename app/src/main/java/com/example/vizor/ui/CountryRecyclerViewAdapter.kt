package com.example.vizor.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.vizor.R

class CountryRecyclerViewAdapter: RecyclerView.Adapter<CountryRecyclerViewAdapter.ViewHolder>(){

private val itemTitles = arrayOf("Canada", "China", "Israel", "New Zealand", "United Kingdom", "United States")
private val itemDescriptions = arrayOf("19 Jan,22 Jan", "28 Jan,1 Feb", "6 Feb,11 Feb", "14 Feb,15 Feb", "22 Feb,12 Mar", "15 Mar,19 Mar")
private val itemImages = intArrayOf(R.drawable.flag_canada, R.drawable.flag_china, R.drawable.flag_israel, R.drawable.flag_newzealand, R.drawable.flag_uk, R.drawable.flag_usa)
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var image: ImageView = itemView.findViewById(R.id.flagImageView)
        var itemName: TextView
    var itemDescription: TextView

    init{
        itemName = itemView.findViewById(R.id.countryNameTV)
        itemDescription = itemView.findViewById(R.id.datesOfEntryTV)

        itemView.setOnClickListener{

            val bundle = bundleOf()
            bundle.putString("country", itemName.text.toString())

            val navController = Navigation.findNavController(itemView)
            navController.navigate(R.id.action_navigation_history_to_countryFragment, bundle)
        }
    }
}

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val v = LayoutInflater.from(parent.context).inflate(R.layout.country_cardview,parent,false)
    return ViewHolder(v)
}

override fun getItemCount(): Int {
    return itemTitles.size
}

override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.itemName.text = itemTitles[position]

    val dates = itemDescriptions[position].split(",")

    holder.itemDescription.text = dates[0] + " to " + dates[1]


    holder.image.setImageResource(itemImages[position])
}
}