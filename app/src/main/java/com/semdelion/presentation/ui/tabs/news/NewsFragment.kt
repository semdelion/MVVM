package com.semdelion.presentation.ui.tabs.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.semdelion.data.core.client.intercepters.NoConnectivityException
import com.semdelion.domain.repositories.news.models.NewsModel
import com.semdelion.presentation.R
import com.semdelion.presentation.core.views.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.semdelion.presentation.core.views.factories.viewModel
import com.semdelion.presentation.databinding.FragmentNewsBinding
import com.semdelion.presentation.core.viewmodels.ListViewState
import com.semdelion.presentation.ui.tabs.news.adapters.NewsRecyclerAdapter


class NewsFragment : BaseFragment() {

    override val viewModel by viewModel<NewsViewModel>()
    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        binding.newsRecyclerview.layoutManager =
            LinearLayoutManager(requireContext().applicationContext)
        val adapter = NewsRecyclerAdapter { news: NewsModel -> viewModel.onItemClick(news) }

        binding.newsRecyclerview.adapter = adapter
        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitList(it.toList())
        }

        binding.newsSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshNews()
                .invokeOnCompletion { binding.newsSwipeRefreshLayout.isRefreshing = false }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.viewState.collectLatest { stateChangeListener(it) }
            }
        }

        initScrollListener()

        return binding.root
    }

    private fun initScrollListener() {
        binding.newsRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val size = viewModel.items.value?.size ?: 0
                if (!viewModel.isLoading && size > 0) {
                    linearLayoutManager?.let {
                        if (it.findLastCompletelyVisibleItemPosition() > (size - 3))
                            viewModel.loadNextPage()
                    }
                }
            }
        })
    }

    private fun stateChangeListener(state: ListViewState) {

        val hasItems = ((viewModel.items.value?.size ?: 0) > 0)
        when (state) {
            is ListViewState.Loading -> {
                binding.newsLoaderProgressBar.visibility =
                    if (hasItems) GONE else VISIBLE
                binding.stateLayout.visibility = GONE
            }

            is ListViewState.Success -> {
                binding.newsLoaderProgressBar.visibility = GONE
                binding.stateLayout.visibility = if (hasItems) GONE else VISIBLE
                if (!hasItems) {
                    binding.stateAnimationView.setAnimation(R.raw.anim_no_data)
                    binding.stateTextview.text = "No news!"
                }
            }

            is ListViewState.Error -> {
                binding.stateLayout.visibility = if (hasItems) GONE else VISIBLE
                binding.newsLoaderProgressBar.visibility = GONE
                if (!hasItems) {
                    val animId =
                        if (state.error is NoConnectivityException) R.raw.anim_no_internet
                        else R.raw.anim_error
                    binding.stateAnimationView.setAnimation(animId)
                    binding.stateTextview.text = state.error.message
                } else {
                    Toast.makeText(context, state.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}