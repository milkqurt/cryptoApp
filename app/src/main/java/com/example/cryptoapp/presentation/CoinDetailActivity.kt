package com.example.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.databinding.ActivityCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    private val binding by lazy {
        ActivityCoinDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
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
            viewModel.getDetailInfo(it).observe(this) {
                with(binding) {
                    tvFromSym.text = it.fromSymbol
                    tvToSym.text = it.toSymbol
                    tvPriceValue.text = it.price.toString()
                    tvMinDayValue.text = it.lowDay.toString()
                    tvMaxDayValue.text = it.highDay.toString()
                    tvLastTraidValue.text = it.lastMarket
                    tvUpdatedValue.text = it.lastUpdate
                    Picasso.get().load(it.imageUrl).into(ivLogoCoin)
                }
            }
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