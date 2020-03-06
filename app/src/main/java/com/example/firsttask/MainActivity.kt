package com.example.firsttask

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firsttask.R.layout
import kotlinx.android.synthetic.main.check_dialog.view.*
import java.util.*
import java.util.Locale.setDefault


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //load and set language from Shared Preferences
        loadLocale()
        setContentView(layout.activity_main)

        //change actionbar title, if you don't change it will be according to your system default language
        changeActionBar()

        val langBtn = findViewById<Button>(R.id.languageButton)
        val messBtn = findViewById<Button>(R.id.messageButton)
        val inBtn = findViewById<Button>(R.id.inputButton)

        langBtn.setOnClickListener() {
            Log.i("MainActivity", "LanguageButton was clicked!")

            //show AlertDialog to display a list of languages, one can be selected
            showChangeLanguageDialog()
        }

        messBtn.setOnClickListener() {
            Log.i("MainActivity", "MessageButton was clicked!")
            //show Toast to display a message
            val toast = Toast.makeText(applicationContext, resources.getString(R.string.languageToast1), Toast.LENGTH_LONG)
            toast.show()
        }

        inBtn.setOnClickListener() {
            Log.i("MainActivity", "InputButton was clicked!")
            //show InputDialog to display editText + Button with conditions
            showInputDialog()
        }
    }

    // set Items from ListView to be clicked + setLocale function
    private fun showChangeLanguageDialog() {
        //getting strings array from xml file to be displayed in AlertDialog
        val langList: Array<String> = resources.getStringArray(R.array.languageList)
        val builder = AlertDialog.Builder(this)

        builder.setItems(langList) { dialog, i ->
            when (i) {
                0 -> {
                    //English
                    setLocale("en")
                    recreate()
                }
                1 -> {
                    //Croatian
                    setLocale("hr")
                    recreate()
                }
                2 -> {
                    //Slovak
                    setLocale("sk")
                    recreate()
                }
                3 -> {
                    //Czech
                    setLocale("cs")
                    recreate()
                }
                4 -> {
                    //Chinese
                    setLocale("zh")
                    recreate()
                }
            }
            //dismiss AlertDialog when language was selected
            dialog.dismiss()
        }
        val dialog = builder.create()
        //show AlertDialog
        dialog.show()
    }

    //configuration + save data to SharedPreferences
    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        setDefault(locale)
        val config = Configuration()
        val configuration = resources.configuration
        configuration.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        //save data to SharedPreferences
        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("My_Lang", lang)
        editor.apply()
    }

    //load language saved in SharedPreferences
    private fun loadLocale() {
        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        if (language != null)
            setLocale(language)
    }

    //create input dialog
    private fun showInputDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.check_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        val mInputDialog = mBuilder.show()

        //setting conditions by button
        mDialogView.checkButton.setOnClickListener {
            val txt = mDialogView.checkDialog.text.toString()
            var j = 0
            var k = 0

            for (i in txt.indices){
                if (txt[i] == 'A' || txt[i] == 'a')
                    j++
                if (txt[i] == '7')
                    k++
            }
            if(j >= 2 && k == 1 && !txt.contains('?') && txt.length in 5..12)
                mInputDialog.dismiss()
            else
                Toast.makeText(applicationContext, resources.getString(R.string.languageToast2), Toast.LENGTH_SHORT).show()
        }
    }

    //change ActionBar
    private fun changeActionBar(){
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = resources.getString(R.string.app_name)
        }
    }
}










