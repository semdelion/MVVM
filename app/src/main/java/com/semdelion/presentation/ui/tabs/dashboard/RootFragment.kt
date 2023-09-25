package com.semdelion.presentation.ui.tabs.dashboard

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController

import com.semdelion.presentation.R
import com.semdelion.presentation.core.views.BaseFragment
import com.semdelion.presentation.core.views.factories.viewModel
import com.semdelion.presentation.databinding.FragmentRootBinding

class RootFragment : BaseFragment() {

    private lateinit var binding: FragmentRootBinding
    override val viewModel by viewModel<RootViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_root,
                container,
                false
            )

        binding.lifecycleOwner = this
        binding.vm = viewModel

        binding.openGreenBoxButton.setOnClickListener {
            openBox(Color.rgb(55,250,55), "Green")

        }
        binding.openYellowBoxButton.setOnClickListener {
            openBox(Color.rgb(255,220,7), "Yellow")
        }
        val liveData = findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(
            BoxFragment.EXTRA_RANDOM_NUMBER
        )
        liveData?.observe(viewLifecycleOwner) {randomNumber ->
            if(randomNumber!=null) {
                Toast.makeText(
                    requireContext(),
                    "Generate number: $randomNumber",
                    Toast.LENGTH_SHORT
                ).show()
                liveData.value = null
            }
        }

        return binding.root
    }

    private fun openBox(color: Int, colorName: String) {
        val direction = RootFragmentDirections.actionRootFragmentToBoxFragment(color, colorName)
        findNavController().navigate(direction)
    }
}