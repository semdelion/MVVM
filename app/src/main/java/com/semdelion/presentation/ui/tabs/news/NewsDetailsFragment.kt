package com.semdelion.presentation.ui.tabs.news

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.generateViewId
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.semdelion.presentation.R
import com.semdelion.presentation.core.views.BaseFragment
import kotlinx.coroutines.launch
import com.semdelion.presentation.core.views.factories.viewModel
import com.semdelion.presentation.databinding.FragmentNewsDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailsFragment : BaseFragment(), MenuProvider {

    override val viewModel by viewModels<NewsDetailsViewModel>()
    private lateinit var binding: FragmentNewsDetailsBinding
    private val args: NewsDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        setupMenu()


        if (viewModel.imageUrl.isNotEmpty()) {
            Glide.with(requireContext().applicationContext).load(viewModel.imageUrl)
                .placeholder(R.drawable.ic_news_placeholder).into(binding.newsDetailsImageview)
        } else {
            binding.newsDetailsImageview.visibility = GONE
        }

        for (creator in viewModel.creators) {
            val creatorView = inflater.inflate(
                R.layout._template_creator, binding.newsDetailsConstraint, false
            )
            creatorView.id = generateViewId()
            creatorView.findViewById<TextView>(R.id.creator).text = creator
            binding.newsDetailsConstraint.addView(creatorView)
            binding.creatorsFlow.addView(creatorView)
        }

        return binding.root;
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onPrepareMenu(menu: Menu) {
        // Handle for example visibility of menu items
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.save_news_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        val id = menuItem.itemId
        if (id == R.id.add_to_favorite_news) {
            lifecycleScope.launch {
                viewModel.addToFavoriteNews()
            }
        }
        return false
    }
}