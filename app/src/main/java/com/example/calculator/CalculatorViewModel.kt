package com.example.calculator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalculatorViewModel : ViewModel() {

    private val _equationText = MutableLiveData("")
    val equationText: LiveData<String> = _equationText

    private val _resultText = MutableLiveData("0")
    val resultText: LiveData<String> = _resultText

    private var isResultShown = false

    fun onButtonClick(btn: String) {
        Log.i("Clicked Button", btn)

        _equationText.value?.let {
            when (btn) {
                "AC" -> {
                    _equationText.value = ""
                    _resultText.value = "0"
                    isResultShown = false
                }
                "C" -> {
                    if (it.isNotEmpty()) {
                        _equationText.value = it.dropLast(1)
                    }
                }
                "=" -> {
                    try {
                        val result = calculateResult(it)
                        _resultText.value = result
                        _equationText.value = result
                        isResultShown = true
                    } catch (_: Exception) {
                        _resultText.value = "Error"
                    }
                }
                else -> {
                    _equationText.value = if (isResultShown) btn else it + btn
                    isResultShown = false
                }
            }
        }
    }

    fun calculateResult(equation: String): String {
        val context: Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable: Scriptable = context.initStandardObjects()
        var finalResult =
            context.evaluateString(scriptable, equation, "Javascript", 1, null).toString()
        if (finalResult.endsWith(".0")) {
            finalResult = finalResult.replace(".0", "")
        }
        return finalResult
    }
}


