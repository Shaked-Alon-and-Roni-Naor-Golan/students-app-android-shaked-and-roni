package com.idz.studentsapp.model

data class Student(
    val name: String,
    val id: String,
    val avatarUrl: String,
    val phoneNumber: String,
    val address: String,
    var isChecked: Boolean
)
