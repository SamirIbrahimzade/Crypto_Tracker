package com.task.cryptotracker.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.cryptotracker.R
import com.task.cryptotracker.data.Currency
import com.task.cryptotracker.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null
    private var cryptoAdapter: CryptoRatesAdapter? = null

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCurrencyRates()
        initCryptos()
        initObservers()
    }

    private fun initCryptos() {
        cryptoAdapter = CryptoRatesAdapter(object : CryptoClickListener {
            override fun onCryptoClicked(position: Int) {
                val currency = viewModel.getCurrency(position)
                val action =
                    HomeFragmentDirections.actionHomeFragmentToCurrencyFragment(
                        currency?.name.toString(),
                        currency?.amount.toString()
                    )

                findNavController().navigate(action)

            }
        })

        binding?.rvCurrencies?.apply {
            adapter = cryptoAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun initObservers() {
        viewModel.currencyRatesLiveData.observe(viewLifecycleOwner) { list ->
            updateRecyclerView(list)
        }
    }

    private fun updateRecyclerView(list: List<Currency>) {
        cryptoAdapter?.clearData()
        list.forEach {
            cryptoAdapter?.addData(it)
        }
    }

}