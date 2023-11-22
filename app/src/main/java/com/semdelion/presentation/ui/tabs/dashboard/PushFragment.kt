package com.semdelion.presentation.ui.tabs.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal
import com.google.firebase.messaging.FirebaseMessaging
import com.semdelion.presentation.R
import com.semdelion.presentation.core.views.BaseFragment
import com.semdelion.presentation.core.views.factories.viewModel
import com.semdelion.presentation.databinding.FragmentPushBinding
import com.semdelion.presentation.services.PushNotificationFirebaseMessagingService
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class PushFragment : BaseFragment() {

    private lateinit var binding: FragmentPushBinding

    override val viewModel by viewModel<PushViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_push, container,false)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        val token1 = PushNotificationFirebaseMessagingService.token
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val token2 = it
                binding.userTokenTextView.text = token2
            }
        }

        binding.sendPushButton.setOnClickListener {
            viewModel.sendPush()
        }

        return binding.root
    }
}