package com.mobile.binancedata.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.binancedata.databinding.RecyclerRowBinding
import com.mobile.binancedata.model.CryptoModel

class CryptoAdapter(private val dataList: List<CryptoModel>) : RecyclerView.Adapter<CryptoAdapter.ViewHolder>() {

    class ViewHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.symbolText.text = dataList[position].symbol
        holder.binding.priceText.text = dataList[position].lastPrice
    }

}