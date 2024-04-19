package com.baig.quiz.model

data class QuestionList( var title: String? = "", var question: MutableMap<String, Questions> = mutableMapOf())
