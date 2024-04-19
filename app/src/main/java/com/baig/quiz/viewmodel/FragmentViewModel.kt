package com.baig.quiz.viewmodel

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baig.quiz.model.QuestionList
import com.baig.quiz.model.Questions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class FragmentViewModel : ViewModel() {

    var quiz: MutableList<QuestionList>? = null
    var counter = 1
    var score = 0
    var maxSize = 0
    var correct = 0
    var wrong = 0
    var noAnswer = 0

    private var questions: Map<String, Questions>? = null
    private var question: Questions? = null
    private lateinit var auth: FirebaseAuth

    private val _update = MutableLiveData<FirebaseUser>()
    val update: LiveData<FirebaseUser>
        get() = _update

    private val _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description

    private val _op1 = MutableLiveData<String>()
    val op1: LiveData<String>
        get() = _op1

    private val _op2 = MutableLiveData<String>()
    val op2: LiveData<String>
        get() = _op2

    private val _op3 = MutableLiveData<String>()
    val op3: LiveData<String>
        get() = _op3

    private val _answer = MutableLiveData<String>()
    val answer: LiveData<String>
        get() = _answer

    private val _timerMax = MutableLiveData<Int>()
    val timerMax: LiveData<Int>
        get() = _timerMax

    val timer = object : CountDownTimer(10000,1000){
        override fun onTick(p0: Long) {
            val timeLeft = p0/1000
            _timerMax.value = timeLeft.toInt()
        }

        override fun onFinish() {
        }
    }

    //firebase authentication and firestore database creation
    fun firebaseAuth(context: Context) {
        auth = FirebaseAuth.getInstance()
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    _update.value = auth.currentUser
                    val uth =  auth.getAccessToken(true)
                    Log.d("Main", "signInAnonymously:success")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Main", "signInAnonymously:failure", task.exception)
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    //reading data from cloud firestore
    fun readDataFromFirestore() {

        val db = FirebaseFirestore.getInstance()
        db.collection("quizz").whereEqualTo("title", "all_questions")
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    quiz = document.toObjects(QuestionList::class.java)
                    question = quiz!![0].question["question$counter"]
                    Log.d("data coming", "DocumentSnapshot data: ${quiz!![0].question.size}")
                    maxSize = quiz!![0].question.size
                    _op1.value = question?.op1!!
                    _op2.value = question?.op2!!
                    _op3.value = question?.op3!!
                    _description.value = question?.description!!
                    _answer.value = question?.answer!!
                } else {
                    Log.d("hello dc", "Something went wrong")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Failure", "get failed with ", exception)
            }
    }

    fun correctIncrease() {
        score += 10
        correct += 1
    }

    fun wrongIncrease() {
        wrong += 1
    }

    fun noAnswer(){
        noAnswer++
    }

    fun increment() {
        counter++
        readDataFromFirestore()
    }

    fun nullify(){
        counter = 1
        score = 0
        correct = 0
        wrong = 0
        noAnswer = 0
    }

}

