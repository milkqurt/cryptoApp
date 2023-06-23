package com.example.cryptoapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.viewmodels.CoinViewModel
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )[CoinViewModel::class.java]
        fromSymbol?.let {
            viewModel.getDetailInfo(it).observe(this, Observer {
                val ivLogoCoin: ImageView = findViewById(R.id.ivLogoCoin);
                val tvFromSym: TextView = findViewById(R.id.tvFromSym);
                val tvToSym: TextView = findViewById(R.id.tvToSym);
                val tvPriceValue: TextView = findViewById(R.id.tvPriceValue);
                val tvMinDayValue: TextView = findViewById(R.id.tvMinDayValue);
                val tvMaxDayValue: TextView = findViewById(R.id.tvMaxDayValue);
                val tvLastTraidValue: TextView = findViewById(R.id.tvLastTraidValue);
                val tvUpdatedValue: TextView = findViewById(R.id.tvUpdatedValue);
                tvFromSym.text = it.fromSymbol
                tvToSym.text = it.toSymbol
                tvPriceValue.text = it.price.toString()
                tvMinDayValue.text = it.lowDay.toString()
                tvMaxDayValue.text = it.highDay.toString()
                tvLastTraidValue.text = it.lastMarket
                tvUpdatedValue.text = it.getFormattedTime()
                Picasso.get().load(it.getFullImageUrl()).into(ivLogoCoin)
            })
        }
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun detailIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}