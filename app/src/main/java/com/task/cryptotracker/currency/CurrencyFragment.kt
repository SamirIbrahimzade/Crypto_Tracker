package com.task.cryptotracker.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.task.cryptotracker.R
import com.task.cryptotracker.databinding.FragmentCurrencyBinding
import com.task.cryptotracker.databinding.FragmentHomeBinding
import com.task.cryptotracker.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CurrencyFragment : Fragment(R.layout.fragment_currency) {

    private var binding: FragmentCurrencyBinding? = null
    private val viewModel: CurrencyViewModel by viewModels()

    val args: CurrencyFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCurrencyBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMaxRate(args.currencyName)
        viewModel.getMinRate(args.currencyName)
        initObservers()
        initUi()
    }

    private fun initUi() {
        initEdittexts()
        initTexts()
        initButtonClicks()
    }

    private fun initEdittexts() {
        binding?.rateMax?.apply {
            etRange.hint = requireContext().getText(R.string.max_hint)
            tvTitle.text = requireContext().getText(R.string.max_rate)
            tvTitle.setTextColor(requireContext().getColor(R.color.light_green))
        }
        binding?.rateMin?.apply {
            tvTitle.text = requireContext().getText(R.string.min_rate)
            etRange.hint = requireContext().getText(R.string.min_hint)
        }
    }

    private fun initTexts() {
        binding?.tvName?.text = args.currencyName
        binding?.tvAmount?.text = args.currencyAmount
    }

    private fun initButtonClicks() {
        binding?.btnSubmit?.setOnClickListener {
            binding?.rateMin?.etRange?.text?.toString()?.let { min ->
                binding?.rateMax?.etRange?.text?.toString()?.let { max ->
                    viewModel.saveRates(args.currencyName, min, max)
                    Toast.makeText(requireContext(), R.string.submit_success, Toast.LENGTH_LONG).show()
                }
            }
        }
        binding?.btnHistory?.setOnClickListener {
            val action =
                CurrencyFragmentDirections.actionCurrencyFragmentToCurrencyHistoryFragment(args.currencyName)

            findNavController().navigate(action)
        }
    }

    private fun initObservers() {
        viewModel.maxRateLiveData.observe(viewLifecycleOwner) { maxRate ->
            binding?.rateMax?.etRange?.setText(maxRate)
        }
        viewModel.minRateLiveData.observe(viewLifecycleOwner) { minRate ->
            binding?.rateMin?.etRange?.setText(minRate)
        }
    }
}