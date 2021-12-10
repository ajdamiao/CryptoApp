package com.example.cryptoapp.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.RviewCryptosBinding
import com.example.cryptoapp.model.Crypto
import java.text.DecimalFormat

class HomeCryptosAdapter(private val cryptos: Crypto) : RecyclerView.Adapter<HomeCryptosAdapter.CryptoViewHolder>() {
    inner class CryptoViewHolder(val binding: RviewCryptosBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding = RviewCryptosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        with(holder) {
            with(cryptos.data[position]) {

                binding.txtCurrencyName.text = name
                binding.txtCurrencySymbol.text = symbol

                setImage(holder, symbol)
                setPriceAndPercentageColor(metrics.market_data.percent_change_usd_last_24_hours, holder)

                val priceFormater = DecimalFormat("#,###.## $")
                val percentageFormater = metrics.market_data.percent_change_usd_last_24_hours

                binding.txtPrice.text = priceFormater.format(metrics.market_data.price_usd)
                binding.txtPercentage.text = "${percentageFormater.toFloat()} %"

            }
        }
    }

    private fun setPriceAndPercentageColor(percentage: Double, holder: CryptoViewHolder) {
        if(percentage > 0){
            holder.binding.txtPrice.setTextColor(Color.parseColor("#8AC135"))
            holder.binding.txtPercentage.setTextColor(Color.parseColor("#8AC135"))
        } else {
            holder.binding.txtPrice.setTextColor(Color.parseColor("#D85877"))
            holder.binding.txtPercentage.setTextColor(Color.parseColor("#D85877"))
        }
    }

    private fun setImage(holder: CryptoViewHolder, symbol: String) {

        if(symbol == "BNB") {
            holder.binding.currencyImage.setImageResource(R.drawable.ic_bnb_currency)
        } else if(symbol.equals("BTC")) {
            holder.binding.currencyImage.setImageResource(R.drawable.ic_btc)
        } else if(symbol == "ADA") {
            holder.binding.currencyImage.setImageResource(R.drawable.ic_ada)
        } else if(symbol == "DOGE") {
            holder.binding.currencyImage.setImageResource(R.drawable.ic_doge)
        } else if(symbol == "ETH") {
            holder.binding.currencyImage.setImageResource(R.drawable.ic_eth)
        } else if(symbol == "USDT") {
            holder.binding.currencyImage.setImageResource(R.drawable.ic_tether)
        }

    }


    override fun getItemCount() = cryptos.data.size
}