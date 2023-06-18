package com.mobile.binancedata.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.binancedata.adapter.CryptoAdapter
import com.mobile.binancedata.databinding.ActivityMainBinding
import com.mobile.binancedata.model.CryptoModel
import com.mobile.binancedata.service.CryptoAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val BASE_URL = "https://data.binance.com"
    private var cryptoAdapter : CryptoAdapter? = null
    private var cryptoModels : ArrayList<CryptoModel>? = null
    private var job : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //API URL -> https://data.binance.com/api/v3/ticker/24hr

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        loadData()

    }

    private fun loadData(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)

        job = CoroutineScope(Dispatchers.IO).launch {

            val response = retrofit.getData()

            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    response.body()?.let {
                        cryptoModels = ArrayList(it)

                        val filteredList = ArrayList(it.filter { model ->
                            model.symbol.endsWith("usdt", ignoreCase = true)
                        })

                        filteredList.let {
                            cryptoAdapter = CryptoAdapter(filteredList)
                            binding.recyclerView.adapter = cryptoAdapter
                        }
                    }
                }
            }
        }
    }
}