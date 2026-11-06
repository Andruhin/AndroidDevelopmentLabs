package com.groupname.rmd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.UUID

class AddTaskActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_task)

    val titleLayout: TextInputLayout = findViewById<TextInputLayout>(R.id.til_title)
    val titleInput = findViewById<TextInputEditText>(R.id.et_title)

    val categorySpinner: Spinner = findViewById<Spinner>(R.id.spinner_category)

    val priorityLabel: TextView = findViewById<TextView>(R.id.tv_priority_label)
    val priorityGroup: RadioGroup = findViewById<RadioGroup>(R.id.rg_priority)

    val locationCheckBox: CheckBox = findViewById<CheckBox>(R.id.cb_location)
    val locationStub: TextView = findViewById<TextView>(R.id.tv_location_stub)

    val saveBtn: MaterialButton = findViewById<MaterialButton>(R.id.btn_save)

    //setup spinner
    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, TaskCategory.entries)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    categorySpinner.adapter = adapter
    categorySpinner.setSelection(TaskCategory.Other.ordinal)

    priorityGroup.setOnCheckedChangeListener { group, i ->
      val colorResId = when(i){
        R.id.rb_low -> R.color.color_priority_low
        R.id.rb_medium -> R.color.color_priority_medium
        else -> R.color.color_priority_high
      }
      val color = ContextCompat.getColor(this, colorResId)
      priorityLabel.setTextColor(color)
    }

    locationCheckBox.setOnCheckedChangeListener { button, isChecked ->
      if (isChecked){
        locationStub.visibility = View.VISIBLE
      } else {
        locationStub.visibility = View.INVISIBLE
      }
    }

    saveBtn.setOnClickListener {
      val taskName = titleInput.text?.toString()
      if (taskName.isNullOrBlank()){
        titleLayout.error = getString(R.string.task_name_error)
        return@setOnClickListener
      } else {
        titleLayout.error = null
      }

      val spinnerPosition = categorySpinner.selectedItemPosition
      val category = TaskCategory.entries.find { it.ordinal == spinnerPosition } ?: TaskCategory.Other

      val checkedBtnId = priorityGroup.checkedRadioButtonId
      val priority = when(checkedBtnId){
        R.id.rb_low -> TaskPriority.Low
        R.id.rb_medium -> TaskPriority.Medium
        else -> TaskPriority.High
      }
      Toast.makeText(this, priority.name, Toast.LENGTH_LONG).show()

      val hasLocation = locationCheckBox.isChecked

      val task = Task(
        id = UUID.randomUUID().toString(),
        title = taskName,
        category = category,
        priority = priority,
        hasLocation = hasLocation
      )
      val intent = Intent()
      intent.putExtra(KEY_NEW_TASK, task)
      setResult(Activity.RESULT_OK, intent)
      finish()
    }


  }

  companion object {
    const val KEY_NEW_TASK = "key new task"
  }
}