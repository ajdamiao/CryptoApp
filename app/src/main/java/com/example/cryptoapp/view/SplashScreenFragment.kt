package com.example.cryptoapp.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import android.view.animation.AnimationUtils
import androidx.navigation.Navigation
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentSplashScreenBinding
import androidx.appcompat.app.AppCompatActivity

class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {
    private lateinit var binding: FragmentSplashScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSplashScreenBinding.bind(view)

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        val slideAnimationLeftToRight = AnimationUtils.loadAnimation(requireContext(), R.anim.side_slide_left_to_right)
        val slideAnimationRightToLeft = AnimationUtils.loadAnimation(requireContext(), R.anim.side_slide_right_to_left)
        val slideAnimationTopToBottom = AnimationUtils.loadAnimation(requireContext(), R.anim.side_slide_top_to_bottom)
        val textAppearanceAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.text_animation_appear)

        binding.splashImageADA.startAnimation(slideAnimationLeftToRight)
        binding.splashImageBNB.startAnimation(slideAnimationTopToBottom)
        binding.splashImageBTC.startAnimation(slideAnimationRightToLeft)
        binding.splashImageDOGE.startAnimation(slideAnimationLeftToRight)
        binding.splashImageETH.startAnimation(slideAnimationRightToLeft)
        binding.txtAppName.startAnimation(textAppearanceAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            openHome()
        }, 3000)
    }

    private fun openHome() {
        Navigation.findNavController(requireView()).navigate(R.id.homeFragment)
    }
}