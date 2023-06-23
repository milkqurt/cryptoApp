package com.example.cryptoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: List<CoinPriceInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder) {
            val symbolsTemp = context.resources.getString(R.string.symbols_template)
            val lastUpdateTemp = context.resources.getString(R.string.last_update_text)
            tvSymbols.text = String.format(symbolsTemp, coin.fromSymbol, coin.toSymbol)
            tvCoinPrice.text = coin.price.toString()
            tvLastUpdate.text = String.format(lastUpdateTemp, coin.getFormattedTime())
            Picasso.get().load(coin.getFullImageUrl()).into(ivLogoCoin)
            itemView.setOnClickListener {
                onCoinClickListener?.onClickCoin(coin)
            }
        }
    }

    override fun getItemCount(): Int = coinInfoList.size

    inner class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivLogoCoin: ImageView = itemView.findViewById(R.id.ivLogoCoin);
        val tvSymbols: TextView = itemView.findViewById(R.id.tvSymbols);
        val tvCoinPrice: TextView = itemView.findViewById(R.id.tvCoinPrice);
        val tvLastUpdate: TextView = itemView.findViewById(R.id.tvLastUpdate);
    }

    interface OnCoinClickListener {
        fun onClickCoin(coinPriceInfo: CoinPriceInfo)
    }
}