package com.semdelion.presentation.ui.tabs.dashboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import com.semdelion.presentation.R
import com.semdelion.presentation.core.views.BaseFragment
import com.semdelion.presentation.databinding.FragmentPushBinding
import com.semdelion.presentation.services.PushNotificationFirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PushFragment : BaseFragment() {

    private lateinit var binding: FragmentPushBinding
    override val viewModel by viewModels<PushViewModel>()

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
        //Переписать и обновлять токены
        //https://firebase.blog/posts/2023/04/managing-cloud-messaging-tokens/#updating-registration-tokens
        //https://github.com/firebase/quickstart-android/blob/master/messaging/app/src/main/java/com/google/firebase/quickstart/fcm/kotlin/MyFirebaseMessagingService.kt

        binding.sendPushButton.setOnClickListener {
            viewModel.sendPush()
        }

        binding.copyImageButton.setOnClickListener {
            val cm: ClipboardManager =
                context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("token", binding.userTokenTextView.text )
            cm.setPrimaryClip(clipData)

            Toast.makeText(context, "Token copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}