package com.example.fragmentstudy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class CurrencyConverterFragment1 : Fragment(){
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.currency_converter_fragment1,
            container,
            false //fragment 붙이기 false
        )
        return view
    }
}