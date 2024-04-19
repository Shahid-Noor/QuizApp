package com.baig.quiz.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.baig.quiz.R
import com.baig.quiz.databinding.FragmentResultsBinding
import com.baig.quiz.preferences.UserData
import com.baig.quiz.viewmodel.FragmentViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FragmentResults : Fragment() {

    private val sharedViewModel: FragmentViewModel by activityViewModels()
    private lateinit var binding: FragmentResultsBinding
    private lateinit var userData: UserData

    @SuppressLint("SetTextI18n")
    @DelicateCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        binding.apply {
            fragment = this@FragmentResults
            viewModel = sharedViewModel
            userData = UserData(requireContext())
            resultPercent.text = sharedViewModel.score.toString()
            GlobalScope.launch {
                userData.storeScore(sharedViewModel.score,sharedViewModel.maxSize)
            }

            resultPercent.text = ((sharedViewModel.correct.toDouble() / sharedViewModel.maxSize.toDouble()) * 100).toInt().toString() + "%"
            resultCorrectText.text = sharedViewModel.correct.toString()
            resultProgress.progress = ((sharedViewModel.correct.toDouble() / sharedViewModel.maxSize.toDouble()) * 100).toInt()
            resultWrongText.text = sharedViewModel.wrong.toString()
            resultMissedText.text = sharedViewModel.noAnswer.toString()
            resultHomeBtn.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentResults_to_fragmentQuiz)
            }
        }
        return binding.root
    }

}