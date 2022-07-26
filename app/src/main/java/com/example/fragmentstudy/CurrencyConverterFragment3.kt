package com.example.fragmentstudy

import android.content.Context
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.lang.Exception
import java.util.*

class CurrencyConverterFragment3 : Fragment(){
    interface CurrencyCalculationListener{
        fun onCalculate(result: Double,
                        amount:Double,
                        from:String,
                        to:String)
    }

    lateinit var listener: CurrencyCalculationListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(activity is CurrencyCalculationListener) { //액티비티가 구현되고 있는가?
            listener = activity as CurrencyCalculationListener
        }
        else{
            throw Exception("CurrencyCalculationListener 미구현") //아니면 예외
        }
    }

    val currencyExchangeMap = mapOf( //맵 사용
        "USD" to 1.0,
        "EUR" to 0.9,
        "JPY" to 110.0,
        "KRW" to 1150.0 //1150원을 기준으로 계산
    )

    fun calculateCurrency(amount:Double, from:String, to:String) : Double{
        var USDAmount = if(from != "USD"){
            (amount / currencyExchangeMap[from]!!)
        }
        else{
            amount
        }
        return currencyExchangeMap[to]!! * USDAmount
    }

    lateinit var fromCurrency: String
    lateinit var toCurrency: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.currency_converter_fragment3,
            container,
            false //fragment 붙이기 false
        )

        val calculateBtn = view.findViewById<Button>(R.id.calculate)
        val amount = view.findViewById<EditText>(R.id.amount)
        val exchangeType = view.findViewById<TextView>(R.id.exchange_type)

        fromCurrency = arguments?.getString("from", "USD")!!// !!널 허용하지 않음
        toCurrency = arguments?.getString("to", "USD")!!
        
        exchangeType.text = "${fromCurrency} => ${toCurrency} 변환"




        calculateBtn.setOnClickListener{
            val result = calculateCurrency(
                amount.text.toString().toDouble(),
                fromCurrency,
                toCurrency
            )
            //result 값을 액티비티로 전달
            listener.onCalculate(
                result,
                amount.text.toString().toDouble(),
                fromCurrency,toCurrency
            )
        }
        return view
    }

    //static 붙인거랑 같음
    companion object{
        fun newInstance(from: String, to: String):CurrencyConverterFragment3{
            val fragment = CurrencyConverterFragment3()

            //번들객체를 만들고 필요한 데이터 저장
            val args = Bundle()
            args.putString("from", from)
            args.putString("to", to)
            fragment.arguments = args

            return fragment
        }
    }
    
}