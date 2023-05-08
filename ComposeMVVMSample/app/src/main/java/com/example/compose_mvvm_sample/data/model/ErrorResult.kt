package com.example.compose_mvvm_sample.data.model

import com.google.gson.annotations.SerializedName

data class ErrorResult(
    @SerializedName("message")
    var message: String,
    @SerializedName("errors")
    val errors: ArrayList<Errors>,
    @SerializedName("documentation_url")
    val documentationUrl: String
)

data class Errors(
    @SerializedName("resource")
    val resource: String,
    @SerializedName("field")
    val field: String,
    @SerializedName("code")
    val code: String
)
