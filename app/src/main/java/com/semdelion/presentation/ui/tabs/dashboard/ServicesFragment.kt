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
import android.widget.Toast
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
import androidx.work.workDataOf
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    activity?.startForegroundService(it)
                }
                else {
                    Toast.makeText(this.context,"startForegroundService is not available in this version android", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.boundButton.setOnClickListener {
            Intent(this.context, BoundService::class.java).also {
                activity?.startService(it)
            }
        }

        binding.workButton.setOnClickListener {
            val workRequest = OneTimeWorkRequestBuilder<UploadWorker>().setInputData(
                workDataOf(
                    UploadWorker.KEY_CONTENT_URI to "https://noticiassalamanca.com/wp-content/uploads/2022/07/vida-eusebio.jpg"
                )
            ).setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            ).build()

            val notifyRequest = OneTimeWorkRequestBuilder<NotifyWorker>()
                .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            ).build()


            WorkManager.getInstance(this.requireContext()).beginUniqueWork("UploadWork",
                ExistingWorkPolicy.REPLACE,
                workRequest)
                .then(notifyRequest)
                .enqueue()
        }

        //DownloadManager https://www.youtube.com/watch?v=4t8EevQSYK4

        binding.periodicWorkButton.setOnClickListener {
            val periodicWorkRequest = PeriodicWorkRequestBuilder<NotifyWorker>(15,TimeUnit.MINUTES).build()

            WorkManager.getInstance(this.requireContext()).enqueueUniquePeriodicWork("NotifyPeriodicWork",
                ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest)
        }

        binding.goToPushButton.setOnClickListener {
            viewModel.goToPushFragment()
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