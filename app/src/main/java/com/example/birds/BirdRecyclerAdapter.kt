package com.example.birds

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore


class BirdRecyclerAdapter(
    val context: Context,
    var birds: MutableList<Bird>
) :
    RecyclerView.Adapter<BirdRecyclerAdapter.ViewHolder>() {

    val layoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item, parent, false)

        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bird = birds[position]
        holder.birdPosition = position

        holder.nameTextView.text = bird.birdname
        holder.placeTextView.text = bird.place
        holder.deleteButton.setOnClickListener {

            removeBird(holder.adapterPosition)

        }


        holder.nameTextView.text = bird.birdname
        holder.placeTextView.text = bird.place
        holder.deleteButton.setOnClickListener {
            Toast.makeText(
                it.context, "Raderar ${holder.adapterPosition}",
                Toast.LENGTH_SHORT
            ).show()

            removeBird(holder.adapterPosition)
        }
    }


    override fun getItemCount() = birds.size


    var removedBirdIds = mutableListOf<String>()

    fun removeBird(position: Int) {
        val birdId = birds[position].id
        removedBirdIds.add(birdId)
        birds.removeAt(position)
        notifyDataSetChanged()


        val sharedPref = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putStringSet("removed_bird_ids", removedBirdIds.toSet())
        editor.apply()
    }

    fun updateBirds(newBirds: MutableList<Bird>) {

        val sharedPref = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        removedBirdIds = sharedPref.getStringSet("removed_bird_ids", emptySet())?.toMutableList() ?: mutableListOf()
        birds.clear()
        birds.addAll(newBirds.filterNot { it.id in removedBirdIds })
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.birdTextView)
        val placeTextView = itemView.findViewById<TextView>(R.id.placeTextView)
        val deleteButton = itemView.findViewById<ImageButton>(R.id.deleteButton)
        var birdPosition = 0


        init {
            itemView.setOnClickListener {
                val intent = Intent(context, BirdInfoActivity::class.java)
                intent.putExtra("birdId", birds[birdPosition].id)

                context.startActivity(intent)

            }

            deleteButton.setOnClickListener {
                removeBird(birdPosition)
            }

        }

    }
}





