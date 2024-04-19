package com.baig.quiz.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.baig.quiz.R
import com.baig.quiz.databinding.FragmentQuizBinding
import com.baig.quiz.viewmodel.FragmentViewModel
import com.google.firebase.auth.FirebaseAuth


class FragmentQuiz : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private val sharedViewModel: FragmentViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentquiz = this
        binding.viewModel = sharedViewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun navigate() {
        findNavController().navigate(R.id.action_fragmentQuiz2_to_fragmentDetails)
    }
}