package com.example.cryptoapp.view.adapter

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.RviewCryptosBinding
import com.example.cryptoapp.model.Crypto
import java.text.DecimalFormat
import java.text.NumberFormat
import kotlin.math.nextUp
import kotlin.math.roundToInt
import kotlin.math.roundToLong

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

                val numberFormatPercentage: NumberFormat = NumberFormat.getNumberInstance()
                numberFormatPercentage.minimumFractionDigits = 2
                numberFormatPercentage.maximumFractionDigits = 2

                binding.txtPrice.text = priceFormater.format(metrics.market_data.price_usd)
                binding.txtPercentage.text = "${numberFormatPercentage.format(metrics.market_data.percent_change_usd_last_24_hours)} %"

                holder.itemView.setOnClickListener {

                    val bundle = Bundle()

                    bundle.putString("cryptoName", name)
                    Navigation.findNavController(itemView).navigate(R.id.cryptoDetailsFragment, bundle)
                }
            }
        }
    }

    private fun setPriceAndPercentageColor(percentage: Double, holder: CryptoViewHolder) {
        if(percentage >= 0){
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
        } else if(symbol == "BTC") {
            holder.binding.currencyImage.setImageResource(R.drawable.ic_btc)
        } else if(symbol == "ADA") {
            holder.binding.currencyImage.setImageResource(R.drawable.ic_ada)
        } else if(symbol == "DOGE") {
            holder.binding.currencyImage.setImageResource(R.drawable.ic_doge)
        } else if(symbol == "ETH") {
            holder.binding.currencyImage.setImageResource(R.drawable.ic_eth)
        } else if(symbol == "USDT") {
            holder.binding.currencyImage.setImageResource(R.drawable.ic_tether)
        } else if(symbol == "SOL") {
            holder.binding.currencyImage.setImageResource(R.drawable.ic_solana)
        }else if(symbol == "USDC") {
            holder.binding.currencyImage.setImageResource(R.drawable.ic_usdcoin)
        }else if(symbol == "XRP") {
            holder.binding.currencyImage.setImageResource(R.drawable.ic_xrp)
        }else if(symbol == "LUNA") {
            holder.binding.currencyImage.setImageResource(R.drawable.ic_luna)
        }else if(symbol == "AVAX") {
            holder.binding.currencyImage.setImageResource(R.drawable.ic_avax)
        }else if(symbol == "SHIB") {
            holder.binding.currencyImage.setImageResource(R.drawable.ic_shiba)
        }else if(symbol == "BUSD") {
            holder.binding.currencyImage.setImageResource(R.drawable.ic_busd)
        }
    }


    override fun getItemCount() = cryptos.data.size
}