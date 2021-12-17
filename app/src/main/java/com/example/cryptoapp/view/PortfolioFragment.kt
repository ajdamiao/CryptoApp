package com.example.cryptoapp.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import com.example.cryptoapp.MainActivity
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentPortfolioBinding
import com.example.cryptoapp.model.Crypto
import com.example.cryptoapp.model.CryptoDetails
import com.example.cryptoapp.util.Util
import com.example.cryptoapp.viewmodel.CryptoDetailsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.JsonArray

import org.json.JSONArray

class PortfolioFragment : Fragment(R.layout.fragment_portfolio) {
    private lateinit var binding: FragmentPortfolioBinding
    private var popupInputDialogView: View? = null
    private val cryptoDetailsViewModel = CryptoDetailsViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPortfolioBinding.bind(view)

        val main = activity as MainActivity
        main.setToolbarBackButtonVisible()
        main.changeToolbarText("Portfolio")

        binding.btnAddCrypto.setOnClickListener {
            addCryptoAlertDialog()
        }

        binding.textView5.setOnClickListener {
            getSharedPref()
        }
    }

    private fun addCryptoAlertDialog()
    {
        val layoutInflater = LayoutInflater.from(requireContext())
        popupInputDialogView = layoutInflater.inflate(R.layout.add_crypto_dialog, null)
        showCryptoAlertDialog()
    }

    private fun showCryptoAlertDialog() {
        context?.let {
            MaterialAlertDialogBuilder(it, R.style.checkInAlertDialog)
                .setView(popupInputDialogView)
                .setPositiveButton("CANCELAR") { dialogInterface, _ ->
                    dialogInterface.cancel()
                }
                .setNegativeButton("CONFIRMAR") { _, _ ->

                    val cryptoName: String = (popupInputDialogView.let { popupInputDialogView?.findViewById<View>(R.id.inputCryptoName) as EditText }).text.toString()
                    val quantity: String = (popupInputDialogView.let { popupInputDialogView?.findViewById<View>(R.id.inputQuantity) as EditText }).text.toString()

                    saveSharedPref(cryptoName, quantity)

                    Util().hideKeyboard(requireContext(),requireView())
                }.show()
        }
    }

    private fun getSharedPref() {
        val prefs = requireActivity().getSharedPreferences("portfolioList", Context.MODE_PRIVATE)

        try {
            val jsonArrayCryptoName = JSONArray(prefs.getString("cryptoName", "[]"))
            val portfolioList = ArrayList<String>()

            for (i in 0 until jsonArrayCryptoName.length()) {
                println(jsonArrayCryptoName.getString(i))

                portfolioList[i] = jsonArrayCryptoName.getString(i)
            }

        } catch (e: Exception) {
            println(" exception: $e")
        }
    }

    private fun saveSharedPref(crypto: String, quantity: String) {
        val pref: SharedPreferences = requireContext().getSharedPreferences("portfolioList", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()

        val jsonCryptoName = JsonArray()
        val jsonCryptoQuantity = JsonArray()

        jsonCryptoName.add(crypto)
        jsonCryptoQuantity.add(quantity)

        editor.apply {
            putString("cryptoName",jsonCryptoName.toString())
            putLong("cryptoQuantity", jsonCryptoQuantity.asLong);
        }.apply()
    }
}