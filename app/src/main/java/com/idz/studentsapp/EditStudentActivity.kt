package com.idz.studentsapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.idz.studentsapp.model.Model
import com.idz.studentsapp.model.Student

class EditStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_student)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toolbar + back arrow
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.title_edit_student)
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Title for this screen
        supportActionBar?.title = getString(R.string.title_edit_student)

        val studentId = intent.getIntExtra("studentId", 0)
        val students = Model.shared.students
        val currStudent: Student? =
            if (studentId in 0 until students.size) students[studentId] else null

        val nameText = findViewById<TextInputEditText>(R.id.edit_student_name_text)
        val idText = findViewById<TextInputEditText>(R.id.edit_student_id_text)
        val phoneText = findViewById<TextInputEditText>(R.id.edit_student_phone_text)
        val addressText = findViewById<TextInputEditText>(R.id.edit_student_address_text)
        val checkbox = findViewById<MaterialCheckBox>(R.id.edit_student_checkbox)

        nameText.setText(currStudent?.name ?: "")
        idText.setText(currStudent?.id ?: "")
        phoneText.setText(currStudent?.phoneNumber ?: "")
        addressText.setText(currStudent?.address ?: "")

        fun updateCheckedText() {
            checkbox.text = if (checkbox.isChecked) "Checked" else "Not checked"
        }

        checkbox.isChecked = currStudent?.isChecked ?: false
        updateCheckedText()

        checkbox.setOnCheckedChangeListener { _, _ ->
            updateCheckedText()
        }

        val saveButton = findViewById<MaterialButton>(R.id.edit_student_save_button)
        val cancelButton = findViewById<MaterialButton>(R.id.edit_student_cancel_button)

        saveButton.setOnClickListener {
            val student = Student(
                nameText.text?.toString() ?: "",
                idText.text?.toString() ?: "",
                "",
                phoneText.text?.toString() ?: "",
                addressText.text?.toString() ?: "",
                checkbox.isChecked
            )

            if (studentId in 0 until students.size) {
                students[studentId] = student
            }

            val intent = Intent(this, StudentDetailsActivity::class.java)
            intent.putExtra("studentId", studentId)
            startActivity(intent)
        }

        cancelButton.setOnClickListener {
            val intent = Intent(this, StudentDetailsActivity::class.java)
            intent.putExtra("studentId", studentId)
            startActivity(intent)
        }
    }
}
