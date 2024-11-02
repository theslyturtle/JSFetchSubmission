package com.jonathansligh.fetchcodingexercise
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jonathansligh.fetchcodingexercise.adapters.ListItemAdapter
import com.jonathansligh.fetchcodingexercise.databinding.ActivityMainBinding
import com.jonathansligh.fetchcodingexercise.models.Item


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ItemListViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var listItemAdapter: ListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ItemListViewModel::class.java]
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        listItemAdapter = ListItemAdapter()
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.itemRecyclerView.adapter = listItemAdapter
        val dividerItemDecoration = DividerItemDecoration(
            binding.itemRecyclerView.context,
            layoutManager.orientation
        )
        binding.itemRecyclerView.layoutManager = layoutManager
        binding.itemRecyclerView.addItemDecoration(dividerItemDecoration)
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getListData(refreshing = true)
            binding.swipeRefreshLayout.isRefreshing = false
        }
        val listObserver = Observer<List<Item>> { newList ->
            // update list UI
            Log.d("MainActivity.getListData", newList.toString())
            listItemAdapter.saveData(newList)
        }

        viewModel.itemListData.observe(this, listObserver)

        val errorObserver = Observer<Boolean> { error ->
            if (error) {
                viewModel.itemListData.value?.let {list ->
                    if (list.isEmpty()) {
                        binding.errorTv.visibility = View.VISIBLE
                    }
                } ?: run {
                    binding.errorTv.visibility = View.VISIBLE
                }
            } else {
                binding.errorTv.visibility = View.GONE
            }
        }

        viewModel.networkError.observe(this, errorObserver)

        // extra livedata needed for edge case where not having internet,
        // then turning back on internet and refreshing
        val refreshErrorObserver = Observer<Boolean> { error ->
            if (error) {
                Toast.makeText(this, resources.getString(R.string.refresh_error), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.refreshError.observe(this, refreshErrorObserver)

    }
}