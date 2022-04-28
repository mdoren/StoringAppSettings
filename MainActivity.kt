package com.example.storingappsettings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {
    lateinit var tag: EditText
    lateinit var pickMain: EditText

    val PREF_NAME = "prefs"
    val PREF_DARK_THEME = "dark_theme"

    override fun onCreate(savedInstanceState: Bundle?) {


        val SP = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val useDarkTheme = SP.getBoolean(PREF_DARK_THEME, false)
        if (useDarkTheme){
            this.setTheme(com.google.android.material.R.style.ThemeOverlay_AppCompat_Dark)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tag = findViewById(R.id.edit1)
        pickMain = findViewById(R.id.edit2)


        val toggle = findViewById<SwitchMaterial>(R.id.switch1)
        toggle.isChecked = useDarkTheme
        toggle.setOnCheckedChangeListener {
            view, isChecked -> toggleTheme(isChecked)
        }
    }

    private fun toggleTheme(darkTheme: Boolean){
        val editor = getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
        editor.apply{
            putBoolean(PREF_DARK_THEME, darkTheme)
            apply()
        }
        val intent = intent
        finish()
        startActivity(getIntent())
    }

    override fun onResume() {
        super.onResume()
        val SP = getSharedPreferences("StoringAppSettings", MODE_PRIVATE)

        // look for key value pairs
        val key1 = SP.getString("name", "")
        val key2 = SP.getString("pickMain", "")


        // assign them to the widgets
        tag!!.setText(key1)
        pickMain!!.setText(key2)

    }

    override fun onPause() {
        super.onPause()

        // opened in private mode for writing
        val SP = getSharedPreferences("StoringAppSettings", MODE_PRIVATE)
        val myEdit = SP.edit()

        //write to the file
        myEdit.putString("tag", tag!!.text.toString())
        myEdit.putString("pickMain", pickMain!!.text.toString())

        // you could use commit
        myEdit.apply()

    }
}
