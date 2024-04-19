package com.baig.quiz.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.baig.quiz.R
import com.baig.quiz.databinding.FragmentDetailsBinding
import com.baig.quiz.preferences.UserData
import com.baig.quiz.viewmodel.FragmentViewModel

class FragmentDetails : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val sharedViewModel: FragmentViewModel by activityViewModels()
    private lateinit var userData: UserData
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.fragment = this
        userData = UserData(requireContext())
        binding.viewModel = sharedViewModel
        observeData()
        return binding.root
    }

    fun navigateTo() {
        sharedViewModel.nullify()
        sharedViewModel.readDataFromFirestore()
        findNavController().navigate(R.id.action_fragmentDetails_to_fragmentQuestoins)
    }

    private fun observeData() {

        this.userData.lastScoreFlow.asLiveData().observe(viewLifecycleOwner) {
            binding.lastScr.text = it.toString()
        }
        this.userData.lastQuestionFlow.asLiveData().observe(viewLifecycleOwner) {
            binding.totalQests.text = it.toString()
        }
    }
}