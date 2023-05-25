package com.demoapp.picassotest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.demoapp.picassotest.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemsList: MutableList<Item>
    private lateinit var requestQueue: RequestQueue


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemsList = mutableListOf()
        requestQueue = Volley.newRequestQueue(this)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        parseJSON()
    }

    private fun parseJSON() {
        val url =
            "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=kitten&image_type=photo&pretty=true"

        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    val jsonArray = response?.getJSONArray("hits")
                    jsonArray?.let {
                        for (i in 0 until it.length()) {
                            val hit = it.getJSONObject(i)
                            val creatorName = hit.getString("user")
                            val imageUrl = hit.getString("webformatURL")
                            val likes = hit.getInt("likes")
                            itemsList.add(Item(imageUrl, creatorName, likes))
                        }
                    }
                    val adapter = ItemAdapter(this@MainActivity, itemsList)
                    binding.recyclerView.adapter = adapter
                } catch (e: JSONException) {
                    Log.e("JSONException", e.message, e)
                }
            }
        ) { error -> Log.e("VolleyError", error?.message, error) }

        requestQueue.add(request)
    }
}