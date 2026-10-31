package com.groupname.rmd

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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

// Екран створення нової задачі
class AddTaskActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_task)

    // Враховуємо системні панелі (статус-бар, навігація): додаємо їхню висоту до відступу з XML
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

    // Посилання на елементи розмітки
    val titleLayout = findViewById<TextInputLayout>(R.id.til_title)
    val titleInput = findViewById<TextInputEditText>(R.id.et_title)
    val categorySpinner = findViewById<Spinner>(R.id.spinner_category)
    val priorityLabel = findViewById<TextView>(R.id.tv_priority_label)
    val priorityGroup = findViewById<RadioGroup>(R.id.rg_priority)
    val locationCheckBox = findViewById<CheckBox>(R.id.cb_location)
    val locationStub = findViewById<TextView>(R.id.tv_location_stub)
    val saveButton = findViewById<MaterialButton>(R.id.btn_save)

    // Вибір категорії: показуємо Toast з назвою вибраного елемента
//    var spinnerInitialized = false
    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, TaskCategories.entries)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    categorySpinner.adapter = adapter
    categorySpinner.setSelection(TaskCategories.Other.ordinal)

    categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        // Spinner викликає слухач один раз при запуску; пропускаємо його,
        // щоб Toast з'являвся лише після вибору користувача
//        if (!spinnerInitialized) {
//          spinnerInitialized = true
//          return
//        }
        Toast.makeText(
          this@AddTaskActivity,
          parent.getItemAtPosition(position).toString(),
          Toast.LENGTH_SHORT,
        ).show()
      }

      // Обов'язковий метод інтерфейсу, нам не потрібен
      override fun onNothingSelected(parent: AdapterView<*>) = Unit
    }

    // Фарбуємо заголовок «Пріоритет»: низький — зелений, високий — червоний, середній — помаранчевий
    fun updatePriorityColor(checkedId: Int) {
      val colorRes = when (checkedId) {
        R.id.rb_low -> R.color.priority_low
        R.id.rb_high -> R.color.priority_high
        else -> R.color.priority_medium
      }
      priorityLabel.setTextColor(ContextCompat.getColor(this, colorRes))
    }
    // Початковий колір відповідає вибраному за замовчуванням пріоритету «Середній»
    updatePriorityColor(priorityGroup.checkedRadioButtonId)
    priorityGroup.setOnCheckedChangeListener { _, checkedId -> updatePriorityColor(checkedId) }

    // Прапорець геолокації: показуємо підказку, коли відмічено, і ховаємо, коли знято
    locationCheckBox.setOnCheckedChangeListener { _, isChecked ->
      locationStub.visibility = if (isChecked) View.VISIBLE else View.GONE
    }

    // Натискання «Зберегти задачу»
    saveButton.setOnClickListener {
      val title = titleInput.text.toString().trim()
      // Порожня назва — показуємо помилку в TextInputLayout і зупиняємо збереження
      if (title.isEmpty()) {
        titleLayout.error = getString(R.string.task_name_error)
        return@setOnClickListener
      }
      // Назва коректна — прибираємо попередню помилку
      titleLayout.error = null

      // Перетворюємо вибрану радіокнопку на значення Priority
      val priority = when (priorityGroup.checkedRadioButtonId) {
        R.id.rb_low -> Priority.LOW
        R.id.rb_high -> Priority.HIGH
        else -> Priority.MEDIUM
      }
      // Записуємо введені поля в об'єкт задачі
      val task = Task(
        title = title,
        category = categorySpinner.selectedItem.toString(),
        priority = priority,
        attachToLocation = locationCheckBox.isChecked,
      )
      // Повідомляємо користувача, що задачу створено
      Snackbar.make(it, R.string.task_created, Snackbar.LENGTH_SHORT).show()
    }
  }
}
