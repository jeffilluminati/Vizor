package com.example.vizor.ui

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vizor.R
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment: Fragment() {
    var recyclerView: RecyclerView? = null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val a = inflater.inflate(R.layout.fragment_history, container, false)
        return a
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = this.requireView().findViewById<RecyclerView>(R.id.countryRecyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, true)
        recyclerView?.adapter = CountryRecyclerViewAdapter()

        var searchTo = view.findViewById<EditText>(R.id.countrySearch)

        searchTo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                // you can call or do what you want with your EditText here

                var posList = listOf<Int>()
                var countryList = (recyclerView!!.adapter as CountryRecyclerViewAdapter).getCountryList()
                for (a in countryList.indices){
                    Log.i(countryList[a].toLowerCase(), requireView().findViewById<EditText>(R.id.countrySearch).text.toString().toLowerCase())
                    if (countryList[a].toLowerCase().contains(requireView().findViewById<EditText>(R.id.countrySearch).text.toString().toLowerCase())){
                        posList += a
                    }
                }

                var posList2 = listOf<Int>()


                val countryDates =  (recyclerView!!.adapter as CountryRecyclerViewAdapter).getCountryDates()
                var startDate = requireView().findViewById<EditText>(R.id.startDateText).text.toString()
                var endDate = requireView().findViewById<EditText>(R.id.endDateText).text.toString()
                for (a in countryDates.indices){
                    if (dateToInt(countryDates[a][1])>= dateToInt(startDate) && dateToInt(countryDates[a][0]) <= dateToInt(endDate)){
                        posList2 += a
                    }
                }

                var posList3 = listOf<Int>()
                for (a in posList){
                    Log.i("a", a.toString())
                    for (b in posList2){
                        if (a == b){
                            posList3 += a
                        }
                    }
                }


                (recyclerView!!.adapter as CountryRecyclerViewAdapter).updateArrays(posList3.toTypedArray())
                // yourEditText...
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.i(s.toString(), "A")
                // you can call or do what you want with your EditText here

                var posList = listOf<Int>()
                var countryList = (recyclerView!!.adapter as CountryRecyclerViewAdapter).getCountryList()
                for (a in countryList.indices){
                    Log.i(countryList[a].toLowerCase(), requireView().findViewById<EditText>(R.id.countrySearch).text.toString().toLowerCase())
                    if (countryList[a].toLowerCase().contains(requireView().findViewById<EditText>(R.id.countrySearch).text.toString().toLowerCase())){
                        posList += a
                    }
                }

                var posList2 = listOf<Int>()


                val countryDates =  (recyclerView!!.adapter as CountryRecyclerViewAdapter).getCountryDates()
                var startDate = requireView().findViewById<EditText>(R.id.startDateText).text.toString()
                var endDate = requireView().findViewById<EditText>(R.id.endDateText).text.toString()
                for (a in countryDates.indices){
                    if (dateToInt(countryDates[a][1])>= dateToInt(startDate) && dateToInt(countryDates[a][0]) <= dateToInt(endDate)){
                        posList2 += a
                    }
                }

                var posList3 = listOf<Int>()
                for (a in posList){
                    Log.i("a", a.toString())
                    for (b in posList2){
                        if (a == b){
                            posList3 += a
                        }
                    }
                }


                (recyclerView!!.adapter as CountryRecyclerViewAdapter).updateArrays(posList3.toTypedArray())
            }
        })

        val searchButton = view.findViewById<ImageButton>(R.id.imageButton)
        searchButton.setOnClickListener(){


        }
        val startDate  = view.findViewById<EditText>(R.id.startDateText)
        startDate.transformIntoEndDatePicker(requireContext(), "dd MMM")

        val endDate  = view.findViewById<EditText>(R.id.endDateText)
        endDate.transformIntoEndDatePicker(requireContext(), "dd MMM")
        startDate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                // you can call or do what you want with your EditText here

                var posList = listOf<Int>()
                var countryList = (recyclerView!!.adapter as CountryRecyclerViewAdapter).getCountryList()
                for (a in countryList.indices){
                    Log.i(countryList[a].toLowerCase(), requireView().findViewById<EditText>(R.id.countrySearch).text.toString().toLowerCase())
                    if (countryList[a].toLowerCase().contains(requireView().findViewById<EditText>(R.id.countrySearch).text.toString().toLowerCase())){
                        posList += a
                    }
                }

                var posList2 = listOf<Int>()


                val countryDates =  (recyclerView!!.adapter as CountryRecyclerViewAdapter).getCountryDates()
                var startDate = requireView().findViewById<EditText>(R.id.startDateText).text.toString()
                var endDate = requireView().findViewById<EditText>(R.id.endDateText).text.toString()
                for (a in countryDates.indices){
                    if (dateToInt(countryDates[a][1])>= dateToInt(startDate) && dateToInt(countryDates[a][0]) <= dateToInt(endDate)){
                        posList2 += a
                    }
                }

                var posList3 = listOf<Int>()
                for (a in posList){
                    Log.i("a", a.toString())
                    for (b in posList2){
                        if (a == b){
                            posList3 += a
                        }
                    }
                }


                (recyclerView!!.adapter as CountryRecyclerViewAdapter).updateArrays(posList3.toTypedArray())
                // yourEditText...
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.i(s.toString(), "A")
                // you can call or do what you want with your EditText here

                var posList = listOf<Int>()
                var countryList = (recyclerView!!.adapter as CountryRecyclerViewAdapter).getCountryList()
                for (a in countryList.indices){
                    Log.i(countryList[a].toLowerCase(), requireView().findViewById<EditText>(R.id.countrySearch).text.toString().toLowerCase())
                    if (countryList[a].toLowerCase().contains(requireView().findViewById<EditText>(R.id.countrySearch).text.toString().toLowerCase())){
                        posList += a
                    }
                }

                var posList2 = listOf<Int>()


                val countryDates =  (recyclerView!!.adapter as CountryRecyclerViewAdapter).getCountryDates()
                var startDate = requireView().findViewById<EditText>(R.id.startDateText).text.toString()
                var endDate = requireView().findViewById<EditText>(R.id.endDateText).text.toString()
                for (a in countryDates.indices){
                    if (dateToInt(countryDates[a][1])>= dateToInt(startDate) && dateToInt(countryDates[a][0]) <= dateToInt(endDate)){
                        posList2 += a
                    }
                }

                var posList3 = listOf<Int>()
                for (a in posList){
                    Log.i("a", a.toString())
                    for (b in posList2){
                        if (a == b){
                            posList3 += a
                        }
                    }
                }


                (recyclerView!!.adapter as CountryRecyclerViewAdapter).updateArrays(posList3.toTypedArray())
            }
        })
        endDate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                // you can call or do what you want with your EditText here

                var posList = listOf<Int>()
                var countryList = (recyclerView!!.adapter as CountryRecyclerViewAdapter).getCountryList()
                for (a in countryList.indices){
                    Log.i(countryList[a].toLowerCase(), requireView().findViewById<EditText>(R.id.countrySearch).text.toString().toLowerCase())
                    if (countryList[a].toLowerCase().contains(requireView().findViewById<EditText>(R.id.countrySearch).text.toString().toLowerCase())){
                        posList += a
                    }
                }

                var posList2 = listOf<Int>()


                val countryDates =  (recyclerView!!.adapter as CountryRecyclerViewAdapter).getCountryDates()
                var startDate = requireView().findViewById<EditText>(R.id.startDateText).text.toString()
                var endDate = requireView().findViewById<EditText>(R.id.endDateText).text.toString()
                for (a in countryDates.indices){
                    if (dateToInt(countryDates[a][1])>= dateToInt(startDate) && dateToInt(countryDates[a][0]) <= dateToInt(endDate)){
                        posList2 += a
                    }
                }

                var posList3 = listOf<Int>()
                for (a in posList){
                    Log.i("a", a.toString())
                    for (b in posList2){
                        if (a == b){
                            posList3 += a
                        }
                    }
                }


                (recyclerView!!.adapter as CountryRecyclerViewAdapter).updateArrays(posList3.toTypedArray())
                // yourEditText...
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.i(s.toString(), "A")
                // you can call or do what you want with your EditText here

                var posList = listOf<Int>()
                var countryList = (recyclerView!!.adapter as CountryRecyclerViewAdapter).getCountryList()
                for (a in countryList.indices){
                    Log.i(countryList[a].toLowerCase(), requireView().findViewById<EditText>(R.id.countrySearch).text.toString().toLowerCase())
                    if (countryList[a].toLowerCase().contains(requireView().findViewById<EditText>(R.id.countrySearch).text.toString().toLowerCase())){
                        posList += a
                    }
                }

                var posList2 = listOf<Int>()


                val countryDates =  (recyclerView!!.adapter as CountryRecyclerViewAdapter).getCountryDates()
                var startDate = requireView().findViewById<EditText>(R.id.startDateText).text.toString()
                var endDate = requireView().findViewById<EditText>(R.id.endDateText).text.toString()
                for (a in countryDates.indices){
                    if (dateToInt(countryDates[a][1])>= dateToInt(startDate) && dateToInt(countryDates[a][0]) <= dateToInt(endDate)){
                        posList2 += a
                    }
                }

                var posList3 = listOf<Int>()
                for (a in posList){
                    Log.i("a", a.toString())
                    for (b in posList2){
                        if (a == b){
                            posList3 += a
                        }
                    }
                }


                (recyclerView!!.adapter as CountryRecyclerViewAdapter).updateArrays(posList3.toTypedArray())
            }
        })

    }




    fun EditText.transformIntoEndDatePicker(context: Context, format: String, maxDate: Date? = null) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

//        val sdfsdf = SimpleDateFormat("dd MMM")
//        var maxDate = sdfsdf.parse(requireView().findViewById<EditText>(R.id.startDateText).toString())

        val myCalendar = Calendar.getInstance()
        val datePickerOnDataSetListener =
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, monthOfYear)
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val sdf = SimpleDateFormat(format, Locale.UK)
                    setText(sdf.format(myCalendar.time))
                }

        setOnClickListener {
            DatePickerDialog(
                    context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {
                for (a in this.javaClass.declaredFields){
                    if (a.name == "mYearPicker"){
                        a.isAccessible = true
                        var yearPicker: View = a.get(this) as View
                        yearPicker.visibility = View.GONE
                    }
                }
                maxDate?.time?.also { datePicker.maxDate = it }
                show()
            }


            var posList = listOf<Int>()
            var countryList = (recyclerView!!.adapter as CountryRecyclerViewAdapter).getCountryList()
            for (a in countryList.indices){
                Log.i(countryList[a].toLowerCase(), requireView().findViewById<EditText>(R.id.countrySearch).text.toString().toLowerCase())
                if (countryList[a].toLowerCase().contains(requireView().findViewById<EditText>(R.id.countrySearch).text.toString().toLowerCase())){
                    posList += a
                }
            }

            var posList2 = listOf<Int>()


            val countryDates =  (recyclerView!!.adapter as CountryRecyclerViewAdapter).getCountryDates()
            var startDate = requireView().findViewById<EditText>(R.id.startDateText).text.toString()
            var endDate = requireView().findViewById<EditText>(R.id.endDateText).text.toString()
            for (a in countryDates.indices){
                if (dateToInt(countryDates[a][1])>= dateToInt(startDate) && dateToInt(countryDates[a][0]) <= dateToInt(endDate)){
                    posList2 += a
                }
            }

            var posList3 = listOf<Int>()
            for (a in posList){
                Log.i("a", a.toString())
                for (b in posList2){
                    if (a == b){
                        posList3 += a
                    }
                }
            }


            (recyclerView!!.adapter as CountryRecyclerViewAdapter).updateArrays(posList3.toTypedArray())
        }
    }

    fun dateToInt(a:String): Int{
        var sum = 0
        var s = a.split(" ")
        sum = when(s[1]){
            "Jan" -> 0
            "Feb" -> 35
            "Mar" -> 70
            "Apr" -> 105
            "May" -> 140
            "Jun" -> 175
            "Jul" -> 210
            "Aug" -> 245
            "Sep" -> 280
            "Oct" -> 315
            "Nov" -> 350
            "Dec" -> 385
            else -> 2
        }
        sum += s[0].toInt()
        return sum
    }
}
