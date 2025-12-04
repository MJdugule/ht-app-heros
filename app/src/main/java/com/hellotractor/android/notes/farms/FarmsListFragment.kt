package com.hellotractor.android.notes.farms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hellotractor.android.notes.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FarmsListFragment : Fragment() {

    private val vm: FarmsListViewModel by viewModels()

    private lateinit var adapter: FarmsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_farms_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val spinner: Spinner = view.findViewById(R.id.sort_spinner)
        val recycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recycler)
        val progress = view.findViewById<View>(R.id.progress)
        val emptyText = view.findViewById<TextView>(R.id.empty_text)
        val errorText = view.findViewById<TextView>(R.id.error_text)
        val swipe = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)

        adapter = FarmsAdapter()
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        val items = listOf(getString(R.string.order_by_date), getString(R.string.order_by_category))
        spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val order = when (position) {
                    0 -> OrderType.DATE
                    else -> OrderType.CATEGORY
                }
                vm.sendEvent(FarmsEvent.ChangeOrder(order))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        swipe.setOnRefreshListener { vm.sendEvent(FarmsEvent.Refresh) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                vm.state.collect { state ->
                    progress.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                    swipe.isRefreshing = state.isLoading

                    if (state.errorMessage != null) {
                        errorText.visibility = View.VISIBLE
                        errorText.text = state.errorMessage
                    } else {
                        errorText.visibility = View.GONE
                    }

                    if (state.notes.isEmpty() && !state.isLoading && state.errorMessage == null) {
                        emptyText.visibility = View.VISIBLE
                        emptyText.text = getString(R.string.no_farms)
                    } else {
                        emptyText.visibility = View.GONE
                    }

                    adapter.submitList(state.notes)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                vm.actions.collect { action ->
                    when (action) {
                        is FarmsAction.ShowError -> {
                            errorText.visibility = View.VISIBLE
                            errorText.text = action.message
                        }
                    }
                }
            }
        }
    }
}
