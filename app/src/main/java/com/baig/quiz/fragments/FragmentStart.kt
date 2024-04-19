package com.baig.quiz.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.baig.quiz.R
import com.baig.quiz.databinding.FragmentStartBinding
import com.baig.quiz.viewmodel.FragmentViewModel

class FragmentStart() : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private val sharedViewModel: FragmentViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        goToNext()
        return binding.root
    }

    //Go to next fragment
    private fun goToNext() {
        sharedViewModel.firebaseAuth(requireContext())

        val timer = object : CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                sharedViewModel.update.observe(viewLifecycleOwner, {
                    if (it != null) {
                        findNavController().navigate(R.id.action_fragmentStart_to_fragmentQuiz)
                    }
                })
            }


        }
        timer.start()
    }
}