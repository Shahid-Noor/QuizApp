package com.baig.quiz.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.baig.quiz.R
import com.baig.quiz.databinding.FragmentQuestoinsBinding
import com.baig.quiz.viewmodel.FragmentViewModel

class FragmentQuestions : Fragment() {

    private lateinit var binding: FragmentQuestoinsBinding
    private var counter = 1
    private val sharedViewModel: FragmentViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestoinsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        binding.apply {
            fragment = this@FragmentQuestions
            viewModel = sharedViewModel

            sharedViewModel.op1.observe(viewLifecycleOwner, {
                quizOptionOne.text = it
            })
            sharedViewModel.op2.observe(viewLifecycleOwner, {
                quizOptionTwo.text = it
            })
            sharedViewModel.op3.observe(viewLifecycleOwner, {
                quizOptionThree.text = it
            })
            sharedViewModel.description.observe(viewLifecycleOwner, {
                quizQuestionNew.text = it
            })
            sharedViewModel.timer.start()
            sharedViewModel.timerMax.observe(viewLifecycleOwner, {
                quizQuestionTime.text = it.toString()
                quizQuestionProgress.progress = it * 10
                if (it == 0) {
                    quizNextBtn.visibility = View.VISIBLE
                    quizOptionOne.isEnabled = false
                    quizOptionTwo.isEnabled = false
                    quizOptionThree.isEnabled = false
                    quizQuestionFeedBack.text = "No Answer Selected"
                    sharedViewModel.noAnswer()
                }
            })

            nextBtnVisible()

            quizNextBtn.setOnClickListener {
                if (counter < sharedViewModel.maxSize) {
                    sharedViewModel.increment()
                    counter++
                    sharedViewModel.timer.start()
                    quizQuestionNumber.text = counter.toString()
                    quizNextBtn.visibility = View.GONE
                    binding.quizOptionOne.isEnabled = true
                    binding.quizOptionTwo.isEnabled = true
                    binding.quizOptionThree.isEnabled = true
                    quizQuestionFeedBack.text = "Answer"
                    quizOptionOne.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.rounded_button)
                    quizOptionTwo.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.rounded_button)
                    quizOptionThree.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.rounded_button)
                } else {
                    findNavController().navigate(R.id.action_fragmentQuestoins_to_fragmentResults2)
                }
            }
        }

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun nextBtnVisible() {
        binding.apply {
            quizOptionOne.setOnClickListener {
                quizNextBtn.visibility = View.VISIBLE
                quizOptionOne.isEnabled = false
                quizOptionTwo.isEnabled = false
                quizOptionThree.isEnabled = false
                sharedViewModel.timer.cancel()

                if (quizOptionOne.text == sharedViewModel.answer.value) {
                    quizQuestionFeedBack.text = "Correct Answer"
                    sharedViewModel.correctIncrease()
                    quizOptionOne.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.true_button)
                } else {
                    quizOptionOne.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.error_button)
                    sharedViewModel.wrongIncrease()
                    quizQuestionFeedBack.text =
                        "Wrong answer" + "\n ${sharedViewModel.answer.value} is right"
                }
            }
        }

        binding.quizOptionTwo.setOnClickListener {
            binding.quizNextBtn.visibility = View.VISIBLE
            binding.quizOptionOne.isEnabled = false
            binding.quizOptionTwo.isEnabled = false
            binding.quizOptionThree.isEnabled = false
            sharedViewModel.timer.cancel()

            if (binding.quizOptionTwo.text == sharedViewModel.answer.value) {
                binding.quizQuestionFeedBack.text = "Correct Answer"
                sharedViewModel.correctIncrease()
                binding.quizOptionTwo.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.true_button)
            } else {
                sharedViewModel.wrongIncrease()
                binding.quizOptionTwo.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.error_button)
                binding.quizQuestionFeedBack.text =
                    "Wrong answer" + "\n ${sharedViewModel.answer.value} is right"
            }
        }
        binding.quizOptionThree.setOnClickListener {
            binding.quizNextBtn.visibility = View.VISIBLE
            binding.quizOptionOne.isEnabled = false
            binding.quizOptionTwo.isEnabled = false
            binding.quizOptionThree.isEnabled = false
            sharedViewModel.timer.cancel()

            if (binding.quizOptionThree.text == sharedViewModel.answer.value) {
                binding.quizQuestionFeedBack.text = "Correct Answer"
                sharedViewModel.correctIncrease()
                binding.quizOptionThree.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.true_button)
            } else {
                binding.quizOptionThree.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.error_button)
                sharedViewModel.wrongIncrease()
                binding.quizQuestionFeedBack.text =
                    "Wrong answer" + "\n ${sharedViewModel.answer.value} is right"
            }
        }
    }
}