package com.example.cryptoapp.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cryptoapp.MainActivity
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentHomeBinding
import com.example.cryptoapp.model.Crypto
import com.example.cryptoapp.view.adapter.HomeCryptosAdapter
import com.example.cryptoapp.viewmodel.HomeViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel = HomeViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        homeViewModel.getCryptos()
        cryptoResponse()
        refreshCryptosAdapter()

        val main = activity as MainActivity

        main.changeToolbarText("Cryptos")
        main.setToolbarBackButtonInvisible()
    }

    private fun refreshCryptosAdapter() {
        val swipeRefreshView = binding.swipeRefresh
        swipeRefreshView.setOnChildScrollUpCallback(object : SwipeRefreshLayout.OnChildScrollUpCallback{
            override fun canChildScrollUp(parent: SwipeRefreshLayout, child: View?): Boolean {
                if(binding.rviewCrypto != null) {
                    return binding.rviewCrypto.canScrollVertically(-1)
                }
                return false
            }
        })

        swipeRefreshView.setOnRefreshListener {
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                homeViewModel.getCryptos()
                swipeRefreshView.isRefreshing = false
            },2000)
        }
    }

    private fun cryptoResponse() {
        homeViewModel.getCryptoResponse().observe(viewLifecycleOwner, { response ->
            when(response) {
                is Crypto -> {
                    setupCryptoRecyclerView(response)
                    binding.progressBar.visibility = View.GONE
                    binding.rviewCrypto.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupCryptoRecyclerView(response: Crypto) {
        binding.rviewCrypto.layoutManager = LinearLayoutManager(requireContext())
        binding.rviewCrypto.adapter = HomeCryptosAdapter(response)
    }
}