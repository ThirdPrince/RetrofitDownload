package com.dhl.retrofitdownload

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dhl.retrofitdownload.databinding.FragmentFirstBinding
import com.dhl.retrofitdownload.model.DownloadState
import com.dhl.retrofitdownload.vm.ImageViewModel
import java.io.File

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null


    private val binding get() = _binding!!

    private val imageViewModel by lazy {
        ViewModelProvider(this)[ImageViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            val targetFile = File(requireActivity().cacheDir, "image.png")
            imageViewModel.getImage(targetFile.absolutePath)
                .observe(viewLifecycleOwner) { downloadState ->
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

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}