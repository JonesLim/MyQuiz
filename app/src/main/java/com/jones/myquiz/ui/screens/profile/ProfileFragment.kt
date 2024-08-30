package com.jones.myquiz.ui.screens.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jones.myquiz.R
import com.jones.myquiz.databinding.FragmentProfileBinding
import com.jones.myquiz.ui.screens.base.BaseFragment
import com.jones.myquiz.ui.screens.profile.viewModel.ProfileViewModelImpl
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val viewModel: ProfileViewModelImpl by viewModels()
    lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pickMedia = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            if (uri != null) {
                viewModel.updateProfilePic(uri)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    }


    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        binding.btnLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        binding.icEditProfilePic.setOnClickListener {
            pickMedia.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }

        lifecycleScope.launch {
            viewModel.user.collect { user ->
                binding.tvUsername.text = user.name
            }
        }

        lifecycleScope.launch {
            viewModel.user.collect { user ->
                binding.tvUserEmail.text = user.email
            }
        }

    }


    override fun setupViewModelObserver(view: View) {
        super.setupViewModelObserver(view)

        lifecycleScope.launch {
            viewModel.finish.collect {
                val action = ProfileFragmentDirections.profileToLogin()
                navController.navigate(action)
            }
        }
        lifecycleScope.launch {
            viewModel.profileUri.collect { uri ->
                Log.d("ProfileFragment", "Image URI: $uri")

                val requestOptions = RequestOptions()
                    .circleCrop()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.ic_profile)
                    .error(R.drawable.ic_error)

                Glide.with(requireContext())
                    .load(uri)
                    .apply(requestOptions)
                    .into(binding.ivProfile as ImageView)
            }
        }

    }

    private fun showLogoutConfirmationDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_logout_confirmation, null)

        AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Yes") { _, _ ->
                viewModel.logout()
            }
            .setNegativeButton("No", null)
            .create()
            .apply {
                setOnShowListener {
                    getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.colorError))
                    getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                }
            }
            .show()
    }

}