package com.groupname.rmd

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.groupname.rmd.SecondActivity.Companion.KEY_ID_EDIT_TASK
import com.groupname.rmd.model.Currency
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date
import java.util.GregorianCalendar

class MainActivity : AppCompatActivity(), ListItemListener {

  val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
  { result ->
    if (result.resultCode == Activity.RESULT_OK){
      val task = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        result.data?.getParcelableExtra(SecondActivity.KEY_TASK, Task::class.java)
      } else {
        result.data?.getParcelableExtra<Task>(SecondActivity.KEY_TASK)
      }
      if (task != null) {
        storage.addTask(task)
        adapter.setData(storage.getTasks())
      }
    }
  }

  private var list: RecyclerView? = null
  private val adapter: ListAdapter = ListAdapter(this)

  private lateinit var storage: Storage

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    loadCurrencyRate()

    list = findViewById<RecyclerView>(R.id.list)
    val layoutManager = LinearLayoutManager(this)
    list?.layoutManager = layoutManager
    list?.adapter = adapter

    storage = Storage(this)
    adapter.setData(storage.getTasks())

    // Додаємо розділювач між елементами
    val divider = DividerItemDecoration(this,  layoutManager.orientation)
    list?.addItemDecoration(divider)

    val btnAddTask = findViewById<Button>(R.id.btn_add)

    btnAddTask.setOnClickListener {
      launchSecondActivityForResult()
    }
  }

  //Launch without waiting for result
  private fun launchSecondActivity(text: String){
    val intent = Intent(this, SecondActivity::class.java)
    intent.putExtra("key_text", text)
    startActivity(intent)
  }

  //Launch with waiting for result
  private fun launchSecondActivityForResult(){
    val intent = Intent(this, SecondActivity::class.java)
   launcher.launch(intent)
  }

  private fun loadCurrencyRate(){
    val request = RetrofitClient.apiService.getCurrencyRate()
    request.enqueue(object : Callback<List<Currency>>{
      override fun onResponse(
        call: Call<List<Currency>?>,
        response: Response<List<Currency>?>
      ) {
        if (response.isSuccessful && response.body() != null){
           val list = response.body()
          val euroCurrency = list?.find { it.currency == "EUR" }

          if (euroCurrency != null){
            runOnUiThread {
              val field = findViewById<TextView>(R.id.currency)
              val buyRate = euroCurrency.buyRate.toDoubleOrNull()
              val saleRate = euroCurrency.saleRate.toDoubleOrNull()

              field.text = "${euroCurrency.currency} ${buyRate}/${saleRate}"
            }
          }
        } else {
          runOnUiThread {
            Toast.makeText(this@MainActivity, "Response is unsuccessful", Toast.LENGTH_SHORT).show()
          }
        }
      }

      override fun onFailure(
        call: Call<List<Currency>?>,
        t: Throwable
      ) {
        runOnUiThread {
          Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
        }
      }
    })

  }

  override fun onItemClick(id: String) {
    val intent = Intent(this, SecondActivity::class.java)
    intent.putExtra(KEY_ID_EDIT_TASK, id)
    startActivity(intent)
  }

  override fun onItemDelete(id: String) {
    storage.deleteItemById(id)
    adapter.setData(storage.getTasks())
  }

}