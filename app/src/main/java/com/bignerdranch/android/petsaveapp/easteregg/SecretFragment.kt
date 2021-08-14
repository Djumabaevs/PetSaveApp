package com.bignerdranch.android.petsaveapp.easteregg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bignerdranch.android.petsaveapp.core.utils.setImage
import com.bignerdranch.android.petsaveapp.databinding.FragmentSecretBinding
import com.djumabaevs.remoteconfig.RemoteConfigUtil

class SecretFragment : Fragment() {

    private val binding get() = _binding!!

    private var _binding: FragmentSecretBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentSecretBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.secretImage.setImage(RemoteConfigUtil.getSecretImageUrl())
    }
}