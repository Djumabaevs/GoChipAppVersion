package com.djumabaevs.gochipapp.pannels

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.djumabaevs.gochipapp.GetAllPetsQuery
import com.djumabaevs.gochipapp.GetPersonsDataQuery
import com.djumabaevs.gochipapp.databinding.VetItemBinding

class VetAdapter(
    private var userPannels: List<GetPersonsDataQuery.Pannel>,
    private var userInfo: GetAllPetsQuery.Persons_pet?
) : RecyclerView.Adapter<VetAdapter.ViewHolder>() {

    class ViewHolder(val binding: VetItemBinding) : RecyclerView.ViewHolder(binding.root)

    var onEndOfListReached: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = VetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pannel = userPannels[position]
        val vetName = pannel.pannel_name
        val personsName = userInfo?.person?.person_name
        val petName = userInfo?.pet?.pet_name
        val panelInfo = pannel.__typename

//        vetName = person.person.person_name
        holder.binding.vetPanelName.text = pannel.pannel_name

        val namePanelClicked = pannel.pannel_name

        holder.binding.vetPanelName.setOnClickListener {

            if(namePanelClicked == "Pet Details") {
                val intent = Intent(holder.itemView.context, VetPanelActivity::class.java)
                intent.putExtra("vetName", vetName )
                intent.putExtra("personName", personsName)
                intent.putExtra("petName", petName)
                holder.itemView.context.startActivity(intent)

            } else if (namePanelClicked == "Digital Identity") {
                val intent = Intent(holder.itemView.context, DigitalIdentityActivity::class.java)
                intent.putExtra("vetName", vetName )
                holder.itemView.context.startActivity(intent)
            }

            else if (namePanelClicked == "Pet ID") {
                val intent = Intent(holder.itemView.context, PetIDActivity::class.java)
                intent.putExtra("vetName", vetName )
                holder.itemView.context.startActivity(intent)
            }

            else if (namePanelClicked == "License Info") {
                val intent = Intent(holder.itemView.context, LicenseInfoActivity::class.java)
                intent.putExtra("vetName", vetName )
                holder.itemView.context.startActivity(intent)
            }

            else if (namePanelClicked == "Vet Details") {
                val intent = Intent(holder.itemView.context, VetDetailsActivity::class.java)
                intent.putExtra("vetName", vetName )
                holder.itemView.context.startActivity(intent)
            }

            else if (namePanelClicked == "Owner Details") {
                val intent = Intent(holder.itemView.context, OwnerDetailsActivity::class.java)
                intent.putExtra("vetName", vetName )
                holder.itemView.context.startActivity(intent)
            }
        }

//        holder.binding.vetPhoneTxt.text = person.person.person_phone
//        holder.binding.vetProfileTxt.text = "profile of user is: " + person.profile_type.toString()

    }

    override fun getItemCount(): Int {
        return userPannels.size
    }

    fun submit(pannels: List<GetPersonsDataQuery.Pannel>, personsNames: GetAllPetsQuery.Persons_pet?) {
        userPannels = pannels
        userInfo = personsNames
        notifyDataSetChanged()
    }
}