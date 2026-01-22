package com.idz.studentsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.idz.studentsapp.model.Model
import com.idz.studentsapp.model.Student

class StudentsListViewActivity : AppCompatActivity() {

    var students: MutableList<Student>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_students_list_view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        students = Model.shared.students
        val listView: ListView = findViewById(R.id.students_list_view)
        listView.adapter = StudentsAdapter()
    }

    inner class StudentsAdapter : BaseAdapter() {
        override fun getCount(): Int = students?.size ?: 0

        override fun getItem(position: Int): Any = students?.get(position) ?: Student("", "", "", "", "", false)

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val inflation = LayoutInflater.from(parent?.context)
            val view = convertView ?: inflation.inflate(R.layout.student_list_row, parent, false).apply {
                findViewById<CheckBox>(R.id.student_row_check_box).apply {
                    setOnClickListener { view ->
                        (tag as? Int)?.let { tag ->
                            val student = students?.get(tag)
                            student?.isChecked = (view as? CheckBox)?.isChecked ?: false
                        }
                    }
                }
            }

            val student = students?.get(position)

            val nameTextView: TextView? = view.findViewById(R.id.student_row_name_text_view)
            val idTextView: TextView? = view.findViewById(R.id.student_row_id_text_view)
            val checkBox: CheckBox? = view.findViewById(R.id.student_row_check_box)
            val checkedTextView: TextView? = view.findViewById(R.id.student_row_checked_text_view)

            nameTextView?.text = student?.name
            idTextView?.text = student?.id

            val isChecked = student?.isChecked ?: false
            checkBox?.apply {
                this.isChecked = isChecked
                tag = position
            }
            checkedTextView?.text = if (isChecked) "Checked" else "Not checked"

            return view
        }
    }
}
