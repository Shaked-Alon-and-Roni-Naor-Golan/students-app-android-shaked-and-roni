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

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_student)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toolbar as ActionBar + back arrow
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.title_add_student)
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val saveButton = findViewById<MaterialButton>(R.id.add_student_activity_save_button)
        val cancelButton = findViewById<MaterialButton>(R.id.add_student_activity_cancel_button)

        val nameEditText = findViewById<TextInputEditText>(R.id.add_student_activity_name_edit_text)
        val idEditText = findViewById<TextInputEditText>(R.id.add_student_activity_id_edit_text)
        val addressEditText = findViewById<TextInputEditText>(R.id.add_student_activity_address_edit_text)
        val phoneEditText = findViewById<TextInputEditText>(R.id.add_student_activity_phone_number_edit_text)

        val checkedBox = findViewById<MaterialCheckBox>(R.id.add_student_activity_checked_checkbox)

        fun updateCheckedText() {
            checkedBox.text = if (checkedBox.isChecked) "Checked" else "Not checked"
        }

        checkedBox.isChecked = false
        updateCheckedText()

        checkedBox.setOnCheckedChangeListener { _, _ ->
            updateCheckedText()
        }

        cancelButton.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            val student = Student(
                nameEditText.text?.toString() ?: "",
                idEditText.text?.toString() ?: "",
                "",
                phoneEditText.text?.toString() ?: "",
                addressEditText.text?.toString() ?: "",
                checkedBox.isChecked
            )

            Model.shared.students.add(student)
            startActivity(Intent(this, StudentsRecyclerViewActivity::class.java))
        }
    }
}
