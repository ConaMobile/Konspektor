package com.conamobile.konspektor.ui.home

import android.os.Bundle
import android.view.View
import com.conamobile.konspektor.R
import com.conamobile.konspektor.core.utils.viewBinding
import com.conamobile.konspektor.databinding.FragmentHomeBinding
import com.conamobile.konspektor.ui.BaseFragment

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val binding by viewBinding { FragmentHomeBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}