package com.task.cryptotracker.currency_history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.cryptotracker.R
import com.task.cryptotracker.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyHistoryFragment : Fragment(R.layout.fragment_history) {

    private var binding: FragmentHistoryBinding? = null
    private val viewModel: CurrencyHistoryViewModel by viewModels()

    private var historyAdapter = CurrencyHistoryAdapter()

    val args: CurrencyHistoryFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHistoryBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        viewModel.getHistory()
        initObservers()
        initRecyclerView()
        binding?.tvTitle?.text = args.currencyName
    }

    private fun initObservers() {
        viewModel.historyLiveData.observe(viewLifecycleOwner) { list ->
            historyAdapter.setData(list.filter { currencyHistory -> currencyHistory.currency.name == args.currencyName })
        }
    }

    private fun initRecyclerView() {
        binding?.rvHistory?.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }
}