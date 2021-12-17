package com.example.cryptoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.example.cryptoapp.databinding.ActivityMainBinding
import com.example.cryptoapp.model.Crypto
import com.example.cryptoapp.view.adapter.HomeCryptosAdapter
import com.example.cryptoapp.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val homeViewModel = HomeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbarInclude.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContent) as NavHostFragment
        navController = navHostFragment.navController

        setToolbar()
        setUpBottomSheet()

        binding.toolbarInclude.btnBack.setOnClickListener {
            navController.popBackStack()
        }

        binding.buttonfloat.setOnClickListener {
            BottomSheetBehavior.from(binding.convertBottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
            homeViewModel.getCryptos()
            getCryptosResponse()
        }

        binding.bottomAppBar.setNavigationOnClickListener {
            navController.navigate(R.id.portifolioFragment)
        }
    }

    private fun getCryptosResponse() {
        homeViewModel.getCryptoResponse().observe(this, { response ->
            when(response) {
                is Crypto -> { setCryptosTypes(response) }
            }
        })
    }

    private fun setCryptosTypes(cryptosTypes: Crypto) {

        val cryptoName = cryptosTypes.data.map { crypto -> crypto.name }

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.convert_simple_menu_item, cryptoName)

        binding.btnSpinner2.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        println(item)
        return super.onOptionsItemSelected(item)
    }

    private fun setUpBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.convertBottomSheet)

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    fun setToolbarBackButtonVisible() {
        binding.toolbarInclude.btnBack.visibility = View.VISIBLE
    }

    fun changeToolbarText(title: String) {
        binding.toolbarInclude.toolbarTitle.text = title
    }

    fun setToolbarBackButtonInvisible() {
        binding.toolbarInclude.btnBack.visibility = View.GONE
    }

    private fun setToolbar() {
        navController.addOnDestinationChangedListener { navController: NavController, nd: NavDestination, _: Bundle? ->
            if (nd.id == R.id.splashScreenFragment) {
                supportActionBar?.hide()
                binding.bottomAppBar.visibility = View.GONE
                binding.buttonfloat.visibility = View.GONE
            } else {
                supportActionBar?.show()
                binding.bottomAppBar.visibility = View.VISIBLE
                binding.buttonfloat.visibility = View.VISIBLE
            }
        }
    }
}