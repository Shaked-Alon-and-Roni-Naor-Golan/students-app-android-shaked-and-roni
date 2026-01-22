package com.idz.studentsapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.idz.studentsapp.model.Model
import com.idz.studentsapp.model.Student

class StudentDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_details)

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
        supportActionBar?.title = getString(R.string.title_student_details)
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val studentId = intent.getIntExtra("studentId", 0)
        val students = Model.shared.students

        val currStudent: Student? =
            if (studentId in 0 until students.size) students[studentId] else null

        val nameText = findViewById<TextView>(R.id.student_details_name_text)
        val idText = findViewById<TextView>(R.id.student_details_id_text)
        val phoneText = findViewById<TextView>(R.id.student_details_phone_text)
        val addressText = findViewById<TextView>(R.id.student_details_address_text)
        val checkbox = findViewById<MaterialCheckBox>(R.id.student_details_checkbox)

        nameText.text = currStudent?.name ?: ""
        idText.text = currStudent?.id ?: ""
        phoneText.text = currStudent?.phoneNumber ?: ""
        addressText.text = currStudent?.address ?: ""

        val isChecked = currStudent?.isChecked ?: false
        checkbox.isChecked = isChecked
        checkbox.text = if (isChecked) "Checked" else "Not checked"

        val backButton = findViewById<MaterialButton>(R.id.student_details_back_button)
        val editButton = findViewById<MaterialButton>(R.id.student_details_edit_button)
        val deleteButton = findViewById<MaterialButton>(R.id.student_details_delete_button)

        backButton.setOnClickListener {
            startActivity(Intent(this, StudentsRecyclerViewActivity::class.java))
        }

        editButton.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("studentId", studentId)
            startActivity(intent)
        }

        deleteButton.setOnClickListener {
            if (studentId in 0 until students.size) {
                students.removeAt(studentId)
            }
            startActivity(Intent(this, StudentsRecyclerViewActivity::class.java))
        }
    }
}
