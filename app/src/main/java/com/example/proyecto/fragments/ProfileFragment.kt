package com.example.proyecto.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyecto.R
import com.example.proyecto.Responses.UsuariResponse
import com.example.proyecto.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var user: UsuariResponse
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getSerializable("usuario") as UsuariResponse

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)

        binding.tvProfileName.text = user.nombre.toUpperCase()
        binding.tvKarma.text = "Karma: ${user.karma}"
        binding.tvPremium.text = "Premium: NO"

        if (user.premium)
            binding.tvPremium.text = "Premium: SI"

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(user: UsuariResponse) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("usuario", user)
                }
            }
    }
}