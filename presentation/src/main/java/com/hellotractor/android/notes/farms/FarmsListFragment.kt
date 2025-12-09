package com.hellotractor.android.notes.farms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hellotractor.notes.presentation.R
import com.hellotractor.notes.presentation.databinding.FragmentFarmsListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FarmsListFragment : Fragment() {

    private val vm: FarmsListViewModel by viewModels()

    private lateinit var adapter: FarmsAdapter
    private var _binding: FragmentFarmsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFarmsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FarmsAdapter()
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        val items = listOf(getString(R.string.order_by_date), getString(R.string.order_by_category))
        binding.sortSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)

        binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val order = when (position) {
                    0 -> OrderType.DATE
                    else -> OrderType.CATEGORY
                }
                vm.sendEvent(FarmsEvent.ChangeOrder(order))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.swipeRefresh.setOnRefreshListener { vm.sendEvent(FarmsEvent.Refresh) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.state.collect { state ->
                    binding.progress.isVisible = state.isLoading
                    binding.swipeRefresh.isRefreshing = state.isLoading
                    binding.errorText.isVisible = state.errorMessage != null
                    binding.errorText.text = state.errorMessage
                    binding.emptyText.isVisible = state.notes.isEmpty() && !state.isLoading && state.errorMessage == null
                    adapter.submitList(state.notes)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
