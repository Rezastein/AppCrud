package com.hgi.harmonifarmhouseadmin

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class CustAdapter(private val userList:ArrayList<CustomData>): RecyclerView.Adapter<CustAdapter.CustViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustAdapter.CustViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_main, parent, false)
        return CustViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustAdapter.CustViewHolder, position: Int) {
        val currentItem = userList[position]
        val type = currentItem.typeKav
        val blok = currentItem.blokKav
        val no = currentItem.noKav
        val statusKa = currentItem.statusKav

        val ketEnd = "$type $blok $no"

        holder.fullName.text = currentItem.fullName
        holder.ket.text = ketEnd
        holder.status.text = "Status : $statusKa"

    }

    override fun getItemCount(): Int {
        return  userList.size
    }

    class CustViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val fullName : TextView = itemView.findViewById(R.id.custTitle)
        val ket : TextView = itemView.findViewById(R.id.ket_customer)
        val status: TextView = itemView.findViewById(R.id.status_customer)
    }

}
