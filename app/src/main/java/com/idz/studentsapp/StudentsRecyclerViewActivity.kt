package com.idz.studentsapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.idz.studentsapp.model.Model
import com.idz.studentsapp.model.Student

interface OnItemClickListener {
    fun onItemClick(position: Int)
    fun onItemClick(student: Student?)
}

class StudentsRecyclerViewActivity : AppCompatActivity() {

    private var students: MutableList<Student>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_students_recycler_view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toolbar as ActionBar (title + subtitle)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = getString(R.string.app_name)          // Students App
        supportActionBar?.subtitle = getString(R.string.title_students_list) // Students list

        students = Model.shared.students

        val recyclerView: RecyclerView = findViewById(R.id.students_recycler_view)
        recyclerView.setHasFixedSize(true)

        val addStudentButton = findViewById<Button>(R.id.activity_students_recycler_view_add_student_button)
        addStudentButton.setOnClickListener {
            startActivity(Intent(this, AddStudentActivity::class.java))
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = StudentsRecyclerAdapter(students)

        val detailsIntent = Intent(this, StudentDetailsActivity::class.java)
        adapter.listener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                detailsIntent.putExtra("studentId", position)
                startActivity(detailsIntent)
            }

            override fun onItemClick(student: Student?) {
                // not used
            }
        }

        recyclerView.adapter = adapter
    }

    class StudentViewHolder(
        itemView: View,
        listener: OnItemClickListener?
    ) : RecyclerView.ViewHolder(itemView) {

        private var nameTextView: TextView? = null
        private var idTextView: TextView? = null
        private var checkBox: CheckBox? = null
        private var checkedTextView: TextView? = null
        private var student: Student? = null

        init {
            nameTextView = itemView.findViewById(R.id.student_row_name_text_view)
            idTextView = itemView.findViewById(R.id.student_row_id_text_view)
            checkBox = itemView.findViewById(R.id.student_row_check_box)
            checkedTextView = itemView.findViewById(R.id.student_row_checked_text_view)

            checkBox?.setOnClickListener { view ->
                val newValue = (view as? CheckBox)?.isChecked ?: false
                student?.isChecked = newValue
                checkedTextView?.text = if (newValue) "Checked" else "Not checked"
            }

            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
                listener?.onItemClick(student)
            }
        }

        fun bind(student: Student?, position: Int) {
            this.student = student

            nameTextView?.text = student?.name
            idTextView?.text = student?.id

            val isChecked = student?.isChecked ?: false

            checkBox?.apply {
                this.isChecked = isChecked
                tag = position
            }

            checkedTextView?.text = if (isChecked) "Checked" else "Not checked"
        }
    }

    class StudentsRecyclerAdapter(private val students: List<Student>?) :
        RecyclerView.Adapter<StudentViewHolder>() {

        var listener: OnItemClickListener? = null

        override fun getItemCount(): Int = students?.size ?: 0

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
            val inflation = LayoutInflater.from(parent.context)
            val view = inflation.inflate(R.layout.student_list_row, parent, false)
            return StudentViewHolder(view, listener)
        }

        override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
            holder.bind(students?.get(position), position)
        }
    }
}
