package com.example.vizor.ui
//
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.activity.viewModels
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.activityViewModels
//import com.android.volley.Request
//import com.android.volley.RequestQueue
//import com.android.volley.Response
//import com.android.volley.VolleyError
//import com.android.volley.toolbox.StringRequest
//import com.android.volley.toolbox.Volley
//import org.json.JSONObject
//import java.text.NumberFormat
//import java.text.SimpleDateFormat
//import java.util.*
//
//class FragmentLocalData : Fragment() {
//
//    fun newInstance(param1: String?, param2: String?): FragmentLocalData? {
//        val fragment: FragmentLocalData= FragmentLocalData()
//        val args = Bundle()
////        args.putString(ARG_PARAM1, param1)
////        args.putString(ARG_PARAM2, param2)
////        fragment.setArguments(args)
//        return fragment
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_data_local, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        var a = requireView().findViewById<ImageView>(R.id.flagImageViewData)
//        var b = requireView().findViewById<TextView>(R.id.countryNameTVData)
//
//        val model: MyViewModel by activityViewModels()
//        b.text = model.getSelectedCountry().name
//
//        var index = 0
//        when(model.getSelectedCountry().name){
//            "Singapore" -> {
//                a.setImageResource(R.drawable.flag_singapore)
//                index = 153
//            }
//            "Israel" -> {a.setImageResource(R.drawable.flag_israel)
//                index=81}
//            "Canada" -> {
//                a.setImageResource(R.drawable.flag_canada)
//                index = 30
//            }
//            "New Zealand" -> {
//                a.setImageResource(R.drawable.flag_newzealand)
//                index = 122
//            }
//            "United States" -> {
//                a.setImageResource(R.drawable.flag_usa)
//                index = 181
//            }
//            "United Kingdom" -> {
//                a.setImageResource(R.drawable.flag_uk)
//                index = 180
//            }
//            "China" -> {
//                a.setImageResource(R.drawable.flag_china)
//                index = 35
//            }
//
//        }
//        val url = "https://api.covid19api.com/summary"
//
//
//        val stringRequest = StringRequest(Request.Method.GET, url, object : Response.Listener<String?> {
//            override fun onResponse(response: String?) {
//                try {
//                    Log.i("WTF", "WT2F2")
//                    val jsonObject = JSONObject(response)
//                    val jsonArray = jsonObject.getJSONArray("Countries")
//                    val jsonObjectChosen = jsonArray.getJSONObject(index)
//                    Log.i("WTF", "WTF29")
//
//                    requireView().findViewById<TextView>(R.id.localConfirmedCases).text = "Total Confirmed cases: " + NumberFormat.getNumberInstance(Locale.US).format(jsonObjectChosen.get("TotalConfirmed"))
//                    requireView().findViewById<TextView>(R.id.localConfirmedRecoveries).text = "Total Confirmed recoveries: " + NumberFormat.getNumberInstance(Locale.US).format(jsonObjectChosen.get("TotalRecovered"))
//                    requireView().findViewById<TextView>(R.id.localConfirmedDeaths).text = "Total Confirmed deaths: " + NumberFormat.getNumberInstance(Locale.US).format(jsonObjectChosen.get("TotalDeaths"))
//                    for (i in 0..1){
////                        countryDB = countryDB + Country(countryNameList.get(i), jsonObjectList.get(i).getString("TotalConfirmed").toInt(), jsonObjectList.get(i).getString("TotalDeaths").toInt(), jsonObjectList.get(i).getString("TotalRecovered").toInt())
//
//                    }
//                    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//                    simpleDateFormat.format(Calendar.getInstance().getTime())
//
//                }
//                catch(e: Exception){
//                    Log.i("WTF", "WTF12")
//                }
//            }
//        }, object : Response.ErrorListener {
//            override fun onErrorResponse(error: VolleyError?) {
//
//                Log.i("WTF", "WTF22")
//
//            }
//        })
//
//        val requestQueue: RequestQueue = Volley.newRequestQueue(context)
//        requestQueue.add(stringRequest)
//    }
//}