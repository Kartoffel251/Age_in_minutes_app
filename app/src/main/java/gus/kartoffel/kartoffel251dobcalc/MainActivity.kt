package gus.kartoffel.kartoffel251dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import gus.kartoffel.kartoffel251dobcalc.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var bindingMain: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
        bindingMain.btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
        bindingMain.tvSelectedDate.text = null
        bindingMain.ageInMinutes.text = null
    }

    private fun clickDatePicker(){
        val myCalendar = Calendar.getInstance()
        val myYear = myCalendar.get(Calendar.YEAR)
        val myMonth = myCalendar.get(Calendar.MONTH)
        val myDay = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
                Toast.makeText(this, "Year was $year, month was ${month+1}, day was $dayOfMonth", Toast.LENGTH_LONG).show()
                val selectedDate = "$dayOfMonth/${month+1}/$year"
                bindingMain.tvSelectedDate.text = selectedDate
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)
                theDate?.let {
                    val selectedDateInMinutes = theDate.time/60000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time/60000
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        val differenceInMinutesText = "%,d".format(differenceInMinutes, Locale.ENGLISH)
                        bindingMain.ageInMinutes.text = differenceInMinutesText
                    }

                }

            },
            myYear,
            myMonth,
            myDay
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}
