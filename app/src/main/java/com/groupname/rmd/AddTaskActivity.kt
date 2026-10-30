package com.groupname.rmd

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddTaskActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_task)

    val root = findViewById<View>(R.id.main)
    val basePadding = root.paddingLeft
    ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
      val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(
        basePadding + bars.left,
        basePadding + bars.top,
        basePadding + bars.right,
        basePadding + bars.bottom,
      )
      insets
    }

    val titleLayout = findViewById<TextInputLayout>(R.id.til_title)
    val titleInput = findViewById<TextInputEditText>(R.id.et_title)
    val categorySpinner = findViewById<Spinner>(R.id.spinner_category)
    val priorityLabel = findViewById<TextView>(R.id.tv_priority_label)
    val priorityGroup = findViewById<RadioGroup>(R.id.rg_priority)
    val locationCheckBox = findViewById<CheckBox>(R.id.cb_location)
    val locationStub = findViewById<TextView>(R.id.tv_location_stub)
    val saveButton = findViewById<MaterialButton>(R.id.btn_save)

    var spinnerInitialized = false
    categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        // Spinner fires once on startup; skip it so the Toast shows only on user selection.
        if (!spinnerInitialized) {
          spinnerInitialized = true
          return
        }
        Toast.makeText(
          this@AddTaskActivity,
          parent.getItemAtPosition(position).toString(),
          Toast.LENGTH_SHORT,
        ).show()
      }

      override fun onNothingSelected(parent: AdapterView<*>) = Unit
    }

    fun updatePriorityColor(checkedId: Int) {
      val colorRes = when (checkedId) {
        R.id.rb_low -> R.color.priority_low
        R.id.rb_high -> R.color.priority_high
        else -> R.color.priority_medium
      }
      priorityLabel.setTextColor(ContextCompat.getColor(this, colorRes))
    }
    updatePriorityColor(priorityGroup.checkedRadioButtonId)
    priorityGroup.setOnCheckedChangeListener { _, checkedId -> updatePriorityColor(checkedId) }

    locationCheckBox.setOnCheckedChangeListener { _, isChecked ->
      locationStub.visibility = if (isChecked) View.VISIBLE else View.GONE
    }

    saveButton.setOnClickListener {
      val title = titleInput.text.toString().trim()
      if (title.isEmpty()) {
        titleLayout.error = getString(R.string.task_name_error)
        return@setOnClickListener
      }
      titleLayout.error = null

      val priority = when (priorityGroup.checkedRadioButtonId) {
        R.id.rb_low -> Priority.LOW
        R.id.rb_high -> Priority.HIGH
        else -> Priority.MEDIUM
      }
      val task = Task(
        title = title,
        category = categorySpinner.selectedItem.toString(),
        priority = priority,
        attachToLocation = locationCheckBox.isChecked,
      )
      Snackbar.make(it, R.string.task_created, Snackbar.LENGTH_SHORT).show()
    }
  }
}
