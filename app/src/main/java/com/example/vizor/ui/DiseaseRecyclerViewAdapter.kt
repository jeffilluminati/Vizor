package com.example.vizor.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.vizor.R

class DiseaseRecyclerViewAdapter: RecyclerView.Adapter<DiseaseRecyclerViewAdapter.ViewHolder>(){

    private val ITEM_TITLES = arrayOf("COVID-19", "Hepatitis B", "Polio", "Diphtheria", "Tetanus", "H1N1", "Typhoid", "Yellow Fever")
    private var ITEM_STATUSES =  arrayOf("Vaccine Pending","Vaccine Pending","Vaccine Pending","Vaccine Pending","Vaccine Pending","Vaccine Pending","Vaccine Pending","Vaccine Pending")
    private val ITEM_IMAGES = intArrayOf(R.drawable.disease_covid, R.drawable.disease_hepatitis, R.drawable.disease_polio, R.drawable.disease_diphtheria, R.drawable.disease_tetanus, R.drawable.disease_h1n1, R.drawable.disease_typhoid, R.drawable.disease_yellowfever)

    private var itemTitles = arrayOf("COVID-19", "Hepatitis B", "Polio", "Diphtheria", "Tetanus", "H1N1", "Typhoid", "Yellow Fever")
    private var itemStatuses = arrayOf("Vaccine Pending","Vaccine Pending","Vaccine Pending","Vaccine Pending","Vaccine Pending","Vaccine Pending","Vaccine Pending","Vaccine Pending")
    private var itemImages = intArrayOf(R.drawable.disease_covid, R.drawable.disease_hepatitis, R.drawable.disease_polio, R.drawable.disease_diphtheria, R.drawable.disease_tetanus, R.drawable.disease_h1n1, R.drawable.disease_typhoid, R.drawable.disease_yellowfever)
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var image: ImageView = itemView.findViewById(R.id.diseaseImageView)
        var itemName: TextView
        var itemDescription: TextView
        var itemCard: CardView

        init{
            itemName = itemView.findViewById(R.id.diseaseName)
            itemDescription = itemView.findViewById(R.id.diseaseStatus)
            itemCard = itemView.findViewById(R.id.diseaseCardView)



            itemView.setOnClickListener{

                val bundle = bundleOf()
                bundle.putString("disease", itemName.text.toString())
                bundle.putString("status", itemDescription.text.toString())

                val navController = Navigation.findNavController(itemView)
                navController.navigate(R.id.action_navigation_vaccines_to_diseaseFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.disease_cardview,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return itemTitles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemName.text = itemTitles[position]
        holder.itemDescription.text = itemStatuses[position]
        holder.image.setImageResource(itemImages[position])

        when(itemStatuses[position]){
            "No Vaccine" -> holder.itemCard.setCardBackgroundColor(holder.itemView.context.resources.getColor(R.color.no_vaccine))
            "Vaccine Pending" -> holder.itemCard.setCardBackgroundColor(holder.itemView.context.resources.getColor(R.color.vaccine_pending))
            "Vaccine Received" -> holder.itemCard.setCardBackgroundColor(holder.itemView.context.resources.getColor(R.color.vaccine_received))
        }
    }

    fun updateArrays(pos: Array<Int>){
        val newList = mutableListOf<String>()
        for (a in pos){
            newList += ITEM_TITLES[a]
        }
        itemTitles = newList.toTypedArray()
        newList.clear()
        for (a in pos){
            newList += ITEM_STATUSES[a]
        }
        itemStatuses = newList.toTypedArray()
        var newList2 =  listOf<Int>()
        for (a in pos){
            newList2 += ITEM_IMAGES[a]
        }
        itemImages = newList2.toIntArray()
        notifyDataSetChanged()
    }

    fun getDiseaseList(): Array<String>{
        return ITEM_TITLES
    }

    fun setStatuses(statuses: Array<String>){
        this.ITEM_STATUSES = statuses
        this.itemStatuses = statuses
        notifyDataSetChanged()
    }

}