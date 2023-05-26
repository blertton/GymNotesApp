package com.example.doitnow


import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.AutoCompleteTextView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_create_card.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



class CreateCard : AppCompatActivity() {

    private lateinit var database: myDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)
        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "To_Do"
        ).build()
        val items = arrayOf("High","Medium","Low")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,items)
        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.create_priority)
        autoCompleteTextView.setAdapter(adapter)




        save_button.setOnClickListener {

            if (create_title.text.toString().trim { it <= ' ' }.isNotEmpty() && create_description.text.toString().trim { it <= ' ' }.isNotEmpty()
                && create_priority.text.toString().trim { it <= ' ' }.isNotEmpty()
            ) {

                var title = create_title.getText().toString()
                var description = create_description.getText().toString()
                var priority = create_priority.getText().toString()
                DataObject.setData(title,description, priority)
                GlobalScope.launch {
                    database.dao().insertTask(Entity(0, title,description, priority))

                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

