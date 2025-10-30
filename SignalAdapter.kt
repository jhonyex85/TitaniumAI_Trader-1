package com.titanium.ai.trader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SignalAdapter(private val items: List<SignalHistoryItem>) : RecyclerView.Adapter<SignalAdapter.VH>() {
    class VH(v: View): RecyclerView.ViewHolder(v) {
        val tvTime: TextView = v.findViewById(R.id.tvTime)
        val tvSignal: TextView = v.findViewById(R.id.tvSignal)
        val tvReason: TextView = v.findViewById(R.id.tvReason)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(LayoutInflater.from(parent.context).inflate(R.layout.item_signal, parent, false))
    override fun onBindViewHolder(holder: VH, position: Int) {
        val it = items[position]
        holder.tvTime.text = it.ts
        holder.tvSignal.text = "${it.signal} @ ${it.price}"
        holder.tvReason.text = it.reason
    }
    override fun getItemCount(): Int = items.size
}
