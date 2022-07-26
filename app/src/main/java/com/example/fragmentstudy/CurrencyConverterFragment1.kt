package com.example.fragmentstudy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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

        val calculateBtn = view.findViewById<Button>(R.id.calculate)
        val amount = view.findViewById<EditText>(R.id.amount)
        val result = view.findViewById<TextView>(R.id.result)
        val fromCurrencySpinner = view.findViewById<Spinner>(R.id.from_currency)
        val toCurrencySpinner = view.findViewById<Spinner>(R.id.to_currency)

        //Spinner(드롭다운)내용 넣기
        val currencySelectionArrayAdapter = ArrayAdapter<String>(
            view.context,
            android.R.layout.simple_spinner_item,
            listOf("USD", "EUR", "JPY", "KRW")
        )
        
        //android.R => 안드로이드에서 미리 만들어 놓은 디자인 가져오기
        currencySelectionArrayAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_item
        )

        fromCurrencySpinner.adapter = currencySelectionArrayAdapter
        toCurrencySpinner.adapter = currencySelectionArrayAdapter

        //object 붙이면 익명클래스
        val itemSelectedListener = object : AdapterView.OnItemSelectedListener{
            //계산하는 메소드 호출 형 변환 많이
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                result.text = calculateCurrency(
                    amount.text.toString().toDouble(),
                    fromCurrencySpinner.selectedItem.toString(),
                    toCurrencySpinner.selectedItem.toString()
                ).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        fromCurrencySpinner.onItemSelectedListener = itemSelectedListener
        toCurrencySpinner.onItemSelectedListener = itemSelectedListener
        return view
    }
}