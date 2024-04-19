package com.baig.quiz.preferences

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserData(context: Context) {

    // Create the dataStore and give it a name same as shared preferences
    private val dataStore = context.createDataStore(name = "user_prefs")

    // Create some keys we will use them to store and retrieve the data
    companion object {
        val LAST_SCORE_KEY = preferencesKey<Int>("LAST_SCORE")
        val TOTAL_QUESTIONS_KEY = preferencesKey<Int>("TOTAL_QUESTIONS")
    }

    // Store user data
    // refer to the data store and using edit
    // we can store values using the keys
    suspend fun storeScore(lastScore: Int, totalQuestion: Int) {
        dataStore.edit {
            it[LAST_SCORE_KEY] = lastScore
            it[TOTAL_QUESTIONS_KEY] = totalQuestion
        // here it refers to the preferences we are editing

        }
    }

    // Create an age flow to retrieve age from the preferences
    // flow comes from the kotlin coroutine
    val lastScoreFlow: Flow<Int> = dataStore.data.map {
        it[LAST_SCORE_KEY] ?: 0
    }

    // Create a name flow to retrieve name from the preferences
    val lastQuestionFlow: Flow<Int> = dataStore.data.map {
        it[TOTAL_QUESTIONS_KEY] ?: 0
    }
}
