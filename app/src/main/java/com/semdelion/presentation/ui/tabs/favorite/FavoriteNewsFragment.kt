package com.semdelion.presentation.ui.tabs.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.semdelion.data.core.intercepters.NoConnectivityException
import com.semdelion.domain.repositories.news.models.NewsModel
import com.semdelion.presentation.R
import com.semdelion.presentation.core.views.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.semdelion.presentation.core.views.factories.viewModel
import com.semdelion.presentation.databinding.FragmentFavoriteNewsBinding
import com.semdelion.presentation.core.viewmodels.ListViewState
import com.semdelion.presentation.ui.tabs.favorite.adapters.FavoriteNewsRecyclerAdapter

class FavoriteNewsFragment : BaseFragment() {

    override val viewModel by viewModel<FavoriteNewsViewModel>()
    private lateinit var binding: FragmentFavoriteNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favorite_news,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.vm = viewModel

        binding.newsRecyclerview.layoutManager =
            LinearLayoutManager(requireContext().applicationContext)

        val adapter = FavoriteNewsRecyclerAdapter { news: NewsModel -> viewModel.onItemClick(news) }
        binding.newsRecyclerview.adapter = adapter
        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.viewState.collectLatest {
                    stateChangeListener(it)
                }
            }
        }
        return binding.root
    }

    private fun stateChangeListener(state: ListViewState) {
        val hasItems = ((viewModel.items.value?.size ?: 0) > 0)
        when (state) {
            is ListViewState.Loading -> {
                binding.newsLoaderProgressBar.visibility =
                    if (hasItems) View.GONE else View.VISIBLE
                binding.stateLayout.visibility = View.GONE
            }

            is ListViewState.Success -> {
                binding.newsLoaderProgressBar.visibility = View.GONE
                binding.stateLayout.visibility = if (hasItems) View.GONE else View.VISIBLE
                if (!hasItems) {
                    binding.stateAnimationView.setAnimation(R.raw.anim_no_data)
                    binding.stateTextview.text = "No news!"
                }
            }

            is ListViewState.Error -> {
                binding.stateLayout.visibility = if (hasItems) View.GONE else View.VISIBLE
                binding.newsLoaderProgressBar.visibility = View.GONE
                if (!hasItems) {
                    val animId =
                        if (state.error is NoConnectivityException) R.raw.anim_no_internet
                        else R.raw.anim_error
                    binding.stateAnimationView.setAnimation(animId)
                    binding.stateTextview.text = state.error.message
                }
            }
        }
    }
}
