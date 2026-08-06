package com.example.taskquotes.data.remote

import com.google.gson.annotations.SerializedName

/** Modelos de datos devueltos por la API REST pública https://opentdb.com/api.php */
data class TriviaQuestionDto(
    @SerializedName("category") val category: String,
    @SerializedName("difficulty") val difficulty: String,
    @SerializedName("question") val question: String,
    @SerializedName("correct_answer") val correctAnswer: String,
    @SerializedName("incorrect_answers") val incorrectAnswers: List<String>
)

data class TriviaListResponse(
    @SerializedName("response_code") val responseCode: Int,
    val results: List<TriviaQuestionDto>
)
