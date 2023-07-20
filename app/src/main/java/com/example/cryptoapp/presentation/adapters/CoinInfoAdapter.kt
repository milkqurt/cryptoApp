package com.example.cryptoapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ItemCoinInfoBinding
import com.example.cryptoapp.domain.CoinInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: List<CoinInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder.binding) {
            val symbolsTemp = context.resources.getString(R.string.symbols_template)
            val lastUpdateTemp = context.resources.getString(R.string.last_update_text)
            tvSymbols.text = String.format(symbolsTemp, coin.fromSymbol, coin.toSymbol)
            tvCoinPrice.text = coin.price.toString()
            tvLastUpdate.text =
                String.format(lastUpdateTemp, coin.lastUpdate)
            Picasso.get().load(coin.imageUrl).into(ivLogoCoin)
            root.setOnClickListener {
                onCoinClickListener?.onClickCoin(coin)
            }
        }
    }

    override fun getItemCount(): Int = coinInfoList.size

    inner class CoinInfoViewHolder(val binding: ItemCoinInfoBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnCoinClickListener {
        fun onClickCoin(coinPriceInfo: CoinInfo)
    }
}