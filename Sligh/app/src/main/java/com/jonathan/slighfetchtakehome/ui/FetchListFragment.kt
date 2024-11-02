package com.jonathan.slighfetchtakehome.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonathan.slighfetchtakehome.R
import com.jonathan.slighfetchtakehome.databinding.FragmentFetchListBinding
import com.jonathan.slighfetchtakehome.ui.adapters.FetchListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FetchListFragment : Fragment() {

    companion object {
        fun newInstance() = FetchListFragment()
    }

    private val viewModel: FetchListViewModel by viewModels()

    private lateinit var _views: FragmentFetchListBinding
    private val views get() = _views

    private var adapter: FetchListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _views = FragmentFetchListBinding.inflate(layoutInflater)
        viewModel.getFetchList()
        viewModel.loading.observe(viewLifecycleOwner) {
            views.swipeRefreshLayout.isRefreshing = it
        }

        adapter = FetchListAdapter()
        views.fetchRecyclerView.adapter = adapter

        viewModel.fetchList.observe(viewLifecycleOwner) {
            adapter?.differ?.submitList(it)
        }

        views.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getFetchList(true)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            //if there is nothing in the database (or not refreshing) and an error from the server/network error
            if (adapter?.differ?.currentList?.isEmpty() == true) {
                views.noConnectionMessage.visibility = if (it) View.VISIBLE else View.GONE
            } else {
                //if we are trying to refresh and there is a server/network error
                if (it) {
                    Toast.makeText(
                        context,
                        getString(R.string.error_message_refresh),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            // covers edge case where no db, api is failing, then a refresh finally succeeds.
            // But there are still a couple of edge cases I'm missing
            if (!it) {
                views.noConnectionMessage.visibility = View.GONE
            }
        }

        return views.root
    }

}