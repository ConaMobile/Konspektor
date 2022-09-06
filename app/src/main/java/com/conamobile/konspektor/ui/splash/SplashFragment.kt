package com.conamobile.konspektor.ui.splash

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.conamobile.konspektor.R

class SplashFragment : Fragment() {
    override fun onStart() {
        super.onStart()
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_textAdderFragment)
        }, 300)
    }
}