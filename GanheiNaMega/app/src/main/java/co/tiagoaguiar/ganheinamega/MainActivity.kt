package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //DECLARANDO AS REFERÊNCIAS DOS OBJETOS DO LAYOUT
        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val resultSaved = prefs.getString("result", null)
        resultSaved.let{
            txtResult.text = "Último resultado foi: $resultSaved"
        }


//        if(resultSaved != null){
//            txtResult.text = "Última aposta foi $resultSaved"
//        }

        //ESCUTANDO EVENTOS DE TOUCH NO BOTÃO
        //OPÇÃO 1 - VIA XML declarando "onClick"
        //OPÇÃO 2 - VIA ViewOnClickListener (interface)
        //OPÇÃO 3 - VIA setOnClickListener direto

        btnGenerate.setOnClickListener {
            val text = editText.text.toString()
            numberGenerator(text, txtResult)

        }//Chave do Listener do BOTÃO

    } //Chave da onCreate

    private fun numberGenerator(text: String, txtResult: TextView) {
        //Testando se vai dar falha de campo vazio
        if (text.isEmpty()) {
            Log.i("Teste", "está vazio")
            Toast.makeText(this, "Favor informar um número entre 6 e 15!", Toast.LENGTH_LONG).show()
            return
        }

        // Testando se vai dar falha de campo fora dos limites 6 e 15
        val qtd = text.toInt()
        if (qtd < 6 || qtd > 15) {
            Log.i("Teste", "número é menor q 6 ou maior que 15")
            Toast.makeText(this, "O número é maior que 15 ou menor que 6", Toast.LENGTH_SHORT).show()
            return
        }

        //Fazendo a randomização porque não deu nenhuma falha
        val numbers = mutableSetOf<Int>()
        val random = Random()
        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)
            if (numbers.size == qtd) {
                break
            }
        }
        txtResult.text = numbers.joinToString(" - ")

        val editor = prefs.edit()

        editor.apply {
            putString("result",txtResult.text.toString())
            apply()
        }


    }


}  //Chave da MainActivity










