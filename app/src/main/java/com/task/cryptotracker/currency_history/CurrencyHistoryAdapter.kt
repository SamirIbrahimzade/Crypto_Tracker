package com.task.cryptotracker.currency_history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.cryptotracker.R
import com.task.cryptotracker.data.CurrencyHistory
import com.task.cryptotracker.databinding.HistoryItemBinding
import java.text.SimpleDateFormat
import java.util.*

class CurrencyHistoryAdapter: RecyclerView.Adapter<CurrencyHistoryAdapter.CurrencyHistoryViewHolder>() {

    private val historyList = mutableListOf<CurrencyHistory>()

    fun setData(list: List<CurrencyHistory>) {
        this.historyList.clear()
        this.historyList.addAll(list)
        notifyDataSetChanged()
    }

    fun addData(historyItem: CurrencyHistory) {
        this.historyList.add(historyItem)
        notifyDataSetChanged()
    }

    fun clearData() {
        this.historyList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHistoryViewHolder {
        return CurrencyHistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CurrencyHistoryViewHolder, position: Int) {
        holder.bindData(historyList[position])
    }

    override fun getItemCount(): Int = historyList.size

    class CurrencyHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = HistoryItemBinding.bind(itemView)

        fun bindData(historyItem: CurrencyHistory) {
            binding.apply {
                tvAmount.text = historyItem.currency.amount.toString()
                tvDate.text = SimpleDateFormat("yyyy-MM-dd hh-mm", Locale.getDefault()).format(historyItem.date)
            }
        }
    }
}
