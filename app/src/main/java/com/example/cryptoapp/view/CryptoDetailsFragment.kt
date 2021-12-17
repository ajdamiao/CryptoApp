package com.example.cryptoapp.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.example.cryptoapp.MainActivity
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentCryptoDetailsBinding
import com.example.cryptoapp.model.Crypto
import com.example.cryptoapp.model.CryptoDetails
import com.example.cryptoapp.viewmodel.CryptoDetailsViewModel
import java.text.DecimalFormat
import java.text.NumberFormat

class CryptoDetailsFragment : Fragment(R.layout.fragment_crypto_details) {
    private lateinit var binding: FragmentCryptoDetailsBinding
    private val cryptoDetailsViewModel = CryptoDetailsViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCryptoDetailsBinding.bind(view)

        val main = activity as MainActivity
        main.setToolbarBackButtonVisible()

        val cryptoName = requireArguments().getString("cryptoName")
        main.changeToolbarText(cryptoName.toString())

        if (cryptoName != null) {
            cryptoDetailsViewModel.getCryptoDetails(cryptoName)
            getCryptoData()
        } else {
            Toast.makeText(requireContext(), "Can't get crypto info",Toast.LENGTH_LONG).show()
        }
    }

    private fun getCryptoData() {
        cryptoDetailsViewModel.getApiResponse().observe(viewLifecycleOwner, { response ->
            when(response) {

                is CryptoDetails -> {
                    binding.progressBarDetails.visibility = View.GONE
                    binding.constraintDetails.visibility = View.VISIBLE
                    setCryptoData(response)
                }

                else -> {
                    Toast.makeText(requireContext(), "Can't get crypto info",Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setCryptoData(response: CryptoDetails) {

        setTxtPriceAndPercentageFormated(response.data.market_data.price_usd, response.data.market_data.percent_change_usd_last_24_hours)
        setPriceAndPercentageColor(response.data.market_data.percent_change_usd_last_24_hours)
        volumeFormat(response.data.market_data.volume_last_24_hours)
        setMainCryptoImage(response.data.symbol)
        convertCryptoToETH(response.data.market_data.price_eth)
        convertCryptoToDollar(response.data.market_data.price_usd)
        setAllPriceHigh(response.data.all_time_high.price)
    }

    private fun setAllPriceHigh(allPriceHigh: Double) {

        val priceFormatter = DecimalFormat("#,###.## $")
        val numberFormatPercentage: NumberFormat = NumberFormat.getNumberInstance()
        numberFormatPercentage.minimumFractionDigits = 2
        numberFormatPercentage.maximumFractionDigits = 2

        binding.txtHigherPrice.text = priceFormatter.format(allPriceHigh)

    }

    private fun volumeFormat(volume: Double) {

        val priceFormatter = DecimalFormat("#,###.## $")
        val numberFormatPercentage: NumberFormat = NumberFormat.getNumberInstance()
        numberFormatPercentage.minimumFractionDigits = 2
        numberFormatPercentage.maximumFractionDigits = 2

        binding.txt24hourvolume.text = priceFormatter.format(volume)
    }

    private fun convertCryptoToDollar(dollarPrice: Double) {

        val priceFormatter = DecimalFormat("#,###.##")

        val numberFormatPercentage: NumberFormat = NumberFormat.getNumberInstance()
        numberFormatPercentage.minimumFractionDigits = 2
        numberFormatPercentage.maximumFractionDigits = 2

        binding.txtDollarPrice.text = priceFormatter.format(dollarPrice)
        binding.txtDollarPrice.text = "${numberFormatPercentage.format(dollarPrice)} "
    }

    private fun convertCryptoToETH(ethPrice: Double) {

        val priceFormatter = DecimalFormat("#,###.####")

        val numberFormatPercentage: NumberFormat = NumberFormat.getNumberInstance()
        numberFormatPercentage.minimumFractionDigits = 4
        numberFormatPercentage.maximumFractionDigits = 4

        binding.txtEthPrice.text = priceFormatter.format(ethPrice)
        binding.txtEthPrice.text = "${numberFormatPercentage.format(ethPrice)} "
    }

    private fun setTxtPriceAndPercentageFormated(price: Double, percentage: Double) {

        val priceFormatter = DecimalFormat("#,###.## $")

        val numberFormatPercentage: NumberFormat = NumberFormat.getNumberInstance()
        numberFormatPercentage.minimumFractionDigits = 2
        numberFormatPercentage.maximumFractionDigits = 2

        binding.txtCryptoPrice.text = priceFormatter.format(price)
        binding.txtCryptoPercentage.text = "${numberFormatPercentage.format(percentage)} %"
    }

    private fun setPriceAndPercentageColor(percentage: Double) {
        if(percentage >= 0){
            binding.txtCryptoPrice.setTextColor(Color.parseColor("#8AC135"))
            binding.txtCryptoPercentage.setTextColor(Color.parseColor("#8AC135"))
        } else {
            binding.txtCryptoPrice.setTextColor(Color.parseColor("#D85877"))
            binding.txtCryptoPercentage.setTextColor(Color.parseColor("#D85877"))
        }
    }

    private fun setMainCryptoImage(symbol: String) {
        if(symbol == "BNB") {
            binding.mainCrypto.setImageResource(R.drawable.ic_bnb_currency)
            binding.mainCrypto2.setImageResource(R.drawable.ic_bnb_currency)
        } else if(symbol == "BTC") {
            binding.mainCrypto.setImageResource(R.drawable.ic_btc)
            binding.mainCrypto2.setImageResource(R.drawable.ic_btc)
        } else if(symbol == "ADA") {
            binding.mainCrypto.setImageResource(R.drawable.ic_ada)
            binding.mainCrypto2.setImageResource(R.drawable.ic_ada)
        } else if(symbol == "DOGE") {
            binding.mainCrypto.setImageResource(R.drawable.ic_doge)
            binding.mainCrypto2.setImageResource(R.drawable.ic_doge)
        } else if(symbol == "ETH") {
            binding.mainCrypto.setImageResource(R.drawable.ic_eth)
            binding.mainCrypto2.setImageResource(R.drawable.ic_eth)
        } else if(symbol == "USDT") {
            binding.mainCrypto.setImageResource(R.drawable.ic_tether)
            binding.mainCrypto2.setImageResource(R.drawable.ic_tether)
        } else if(symbol == "SOL") {
            binding.mainCrypto.setImageResource(R.drawable.ic_solana)
            binding.mainCrypto2.setImageResource(R.drawable.ic_solana)
        }else if(symbol == "USDC") {
            binding.mainCrypto.setImageResource(R.drawable.ic_usdcoin)
            binding.mainCrypto2.setImageResource(R.drawable.ic_usdcoin)
        }else if(symbol == "XRP") {
            binding.mainCrypto.setImageResource(R.drawable.ic_xrp)
            binding.mainCrypto2.setImageResource(R.drawable.ic_xrp)
        }else if(symbol == "LUNA") {
            binding.mainCrypto.setImageResource(R.drawable.ic_luna)
            binding.mainCrypto2.setImageResource(R.drawable.ic_luna)
        }else if(symbol == "AVAX") {
            binding.mainCrypto.setImageResource(R.drawable.ic_avax)
            binding.mainCrypto2.setImageResource(R.drawable.ic_avax)
        }else if(symbol == "SHIB") {
            binding.mainCrypto.setImageResource(R.drawable.ic_shiba)
            binding.mainCrypto2.setImageResource(R.drawable.ic_shiba)
        }else if(symbol == "BUSD") {
            binding.mainCrypto.setImageResource(R.drawable.ic_busd)
            binding.mainCrypto2.setImageResource(R.drawable.ic_busd)
        }
    }
}