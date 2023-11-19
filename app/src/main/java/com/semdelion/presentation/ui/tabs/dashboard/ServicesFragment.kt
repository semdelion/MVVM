package com.semdelion.presentation.ui.tabs.dashboard

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.semdelion.presentation.R
import com.semdelion.presentation.core.views.BaseFragment
import com.semdelion.presentation.core.views.factories.viewModel
import com.semdelion.presentation.databinding.FragmentServicesBinding
import com.semdelion.presentation.services.BackgroundServices
import com.semdelion.presentation.services.BoundService
import com.semdelion.presentation.services.ForegroundService
import com.semdelion.presentation.services.works.NotifyWorker
import com.semdelion.presentation.services.works.UploadWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ServicesFragment : BaseFragment() {
    private lateinit var binding: FragmentServicesBinding
    override val viewModel by viewModel<ServicesViewModel>()

    private val connectivity = object: ServiceConnection {

        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            val uploadBinder = (service as BoundService.UploadBinder)
            uploadBinder.subscribeToProgress {progress ->
                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        binding.boundProgressText.text = "Bound service progress ${progress}%"
                    }
                }
            }
        }
        override fun onServiceDisconnected(p0: ComponentName?) { }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_services, container,false)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        binding.backgroundButton.setOnClickListener {
            Intent(this.context, BackgroundServices::class.java).also {
                activity?.startService(it)
            }
        }

        binding.foregroundButton.setOnClickListener {
            Intent(this.context, ForegroundService::class.java).also {
                activity?.startForegroundService(it)
            }
        }

        binding.boundButton.setOnClickListener {
            Intent(this.context, BoundService::class.java).also {
                activity?.startService(it)
            }
        }

        binding.workButton.setOnClickListener {
            val workRequest = OneTimeWorkRequest.from(UploadWorker::class.java)
            val notifyRequest = OneTimeWorkRequestBuilder<NotifyWorker>()
                .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            ).build()

           /* WorkManager.getInstance(this.requireContext()).enqueueUniqueWork("doWork",
                ExistingWorkPolicy.REPLACE,
                workRequest)*/

            WorkManager.getInstance(this.requireContext()).beginUniqueWork("UploadWork",
                ExistingWorkPolicy.REPLACE,
                workRequest)
                .then(notifyRequest)
                .enqueue()
        }

        binding.periodicWorkButton.setOnClickListener {
            val periodicWorkRequest = PeriodicWorkRequestBuilder<NotifyWorker>(15,TimeUnit.MINUTES).build()

            WorkManager.getInstance(this.requireContext()).enqueueUniquePeriodicWork("NotifyPeriodicWork",
                ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest)
        }

        Intent(this.context, BoundService::class.java).also {
            requireActivity().bindService(it, connectivity, BIND_AUTO_CREATE)
        }
        return binding.root
    }

    override fun onDestroyView() {
        requireActivity().unbindService(connectivity)
        Intent(this.context, BoundService::class.java).also {
            requireActivity().stopService(it)
        }
        super.onDestroyView()
    }
}