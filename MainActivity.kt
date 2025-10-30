package com.titanium.ai.trader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.TextView
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import java.time.Instant
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var history = mutableListOf<SignalHistoryItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtPair = findViewById<EditText>(R.id.edtPair)
        val btnAnalyze = findViewById<Button>(R.id.btnAnalyze)
        val txtSignal = findViewById<TextView>(R.id.txtSignal)
        val rvHistory = findViewById<RecyclerView>(R.id.rvHistory)
        val btnDesistir = findViewById<Button>(R.id.btnDesistir)

        rvHistory.layoutManager = LinearLayoutManager(this)
        rvHistory.adapter = SignalAdapter(history)

        edtPair.setText("EUR/USD")

        btnAnalyze.setOnClickListener {
            val pair = edtPair.text.toString().trim().uppercase()
            if (pair.isEmpty()) {
                txtSignal.text = "Digite um par, ex: EUR/USD"
                return@setOnClickListener
            }
            val res = simulateAnalysis(pair)
            txtSignal.text = "${res.signal} @ ${res.price}\n${res.reason}"
            history.add(0, SignalHistoryItem(ts = Instant.now().toString(), signal = res.signal, reason = res.reason, price = res.price))
            rvHistory.adapter?.notifyDataSetChanged()
            if (res.signal != "HOLD") {
                NotificationHelper.show(this, "Titanium AI — ${res.signal}", "${pair} ${res.signal} @ ${res.price}")
            }
        }

        btnDesistir.setOnClickListener {
            finish()
        }
    }

    private fun simulateAnalysis(pair: String): SignalResponse {
        val base = when {
            pair.contains("BTC") || pair.contains("ETH") -> Random.nextDouble(1000.0, 60000.0)
            pair.contains("BRL") -> Random.nextDouble(4.0, 6.0)
            else -> Random.nextDouble(0.5, 1.6)
        }
        val r = Random.nextDouble()
        val signal = when {
            r < 0.33 -> "BUY"
            r < 0.66 -> "SELL"
            else -> "HOLD"
        }
        val reason = when (signal) {
            "BUY" -> "EMA curto cruzou acima + momentum simulado"
            "SELL" -> "EMA curto cruzou abaixo + pressão de venda simulada"
            else -> "Sem condições claras"
        }
        return SignalResponse(symbol = pair, timestamp = Instant.now().toString(), signal = signal, price = String.format("%.5f", base).toDouble(), reason = reason)
    }
}
