package com.idz.studentsapp.model

class Model private constructor() {

    val students: MutableList<Student> = ArrayList()

    companion object {
        val shared = Model()
    }

    init {
        for (i in 0..10) {
            val student = Student(
                name = "Student $i",
                id = "$i",
                avatarUrl = "",
                address = "Habsor",
                phoneNumber = "0546885660",
                isChecked = false
            )
            students.add(student)
        }
    }
}