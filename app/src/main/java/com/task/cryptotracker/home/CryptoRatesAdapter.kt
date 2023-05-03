package com.task.cryptotracker.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.cryptotracker.R
import com.task.cryptotracker.data.Currency
import com.task.cryptotracker.databinding.CurrencyItemBinding

class CryptoRatesAdapter(private val cryptoClickListener: CryptoClickListener) :
    RecyclerView.Adapter<CryptoRatesAdapter.CryptoRatesViewHolder>() {

    private val cryptos = mutableListOf<Currency>()

    fun setData(list: List<Currency>) {
        this.cryptos.clear()
        this.cryptos.addAll(list)
        notifyDataSetChanged()
    }

    fun addData(currency: Currency) {
        this.cryptos.add(currency)
        notifyDataSetChanged()
    }

    fun clearData() {
        this.cryptos.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoRatesViewHolder {
        return CryptoRatesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.currency_item, parent, false)
        ).apply {
            this.itemView.setOnClickListener { cryptoClickListener.onCryptoClicked(adapterPosition) }
        }
    }

    override fun onBindViewHolder(holder: CryptoRatesViewHolder, position: Int) {
        holder.bindData(cryptos[position])
    }

    override fun getItemCount(): Int = cryptos.size

    class CryptoRatesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = CurrencyItemBinding.bind(itemView)

        fun bindData(currency: Currency) {
            binding.apply {
                tvTitle.text = currency.name
                tvAmount.text = currency.amount.toString()
            }
        }
    }
}
