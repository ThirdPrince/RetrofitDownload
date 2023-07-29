package com.dhl.retrofitdownload

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dhl.retrofitdownload.databinding.FragmentSecondBinding
import com.dhl.retrofitdownload.model.DownloadState
import com.dhl.retrofitdownload.vm.ImageViewModel
import com.dhl.retrofitdownload.vm.ImageViewModelByFlow
import java.io.File

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val imageViewModel :ImageViewModelByFlow by lazy {
        ViewModelProvider(this)[ImageViewModelByFlow::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                val targetFile = File(requireActivity().cacheDir, "image.png")
                imageViewModel.getImage2(targetFile.absolutePath).collect{
                    downloadState ->
                    when (downloadState) {
                        is DownloadState.Downloading -> {
                            binding.buttonFirst.text = "${downloadState.progress}%"
                        }

                        is DownloadState.DownloadFinish -> {
                            val bitmap = BitmapFactory.decodeFile(downloadState.filePath)
                            binding.imageView.setImageBitmap(bitmap)
                            binding.buttonFirst.text = "下载完成"
                        }

                        is DownloadState.Failed -> {
                            binding.buttonFirst.text = downloadState.error
                        }

                    }
                }

            }
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}