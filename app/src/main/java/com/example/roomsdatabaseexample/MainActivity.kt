package com.example.roomsdatabaseexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.os.HandlerCompat.postDelayed
import android.R.attr.name
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private lateinit var tv_showAll: TextView
    private lateinit var tv_adddata: TextView
    private lateinit var tv_alldata: TextView
    private lateinit var et_firstname: TextView
    private lateinit var et_lastname: TextView
    private lateinit var et_age: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_showAll = findViewById(R.id.tv_showAll)
        tv_adddata = findViewById(R.id.tv_adddata)
        tv_alldata = findViewById(R.id.tv_alldata)
        et_firstname = findViewById(R.id.et_firstname)
        et_lastname = findViewById(R.id.et_lastname)
        et_age = findViewById(R.id.et_age)

        tv_adddata.setOnClickListener {
            var handler = Handler()
            val r = Runnable {
                addData()
            }
            handler.postDelayed(r, 1000)

        }
        tv_showAll.setOnClickListener {
            var handler = Handler()
            val r = Runnable {
                val userList = AppDatabase.getAppDatabase(this@MainActivity).userDao().getAll()
                Log.d("Main Activity", "Rows Count: " + userList.size)
                for (elem_ in userList) {
                    tv_alldata.append(elem_.firstName + " " + elem_.lastName + " " + elem_.age + "\n\n");
                    // System.out.println(elem_.firstName +" "+elem_.lastName+" "+elem_.age)
                }
            }

            handler.postDelayed(r, 1000)


        }
    }

    private fun addData() {
        if (et_firstname.text.toString().trim().equals("")) {
            return
        }
        if (et_lastname.text.toString().trim().equals("")) {
            return
        }
        if (et_age.text.toString().trim().equals("")) {
            return
        }
        var user = User()
        user.firstName = et_firstname.text.toString().trim()
        user.lastName = et_lastname.text.toString().trim()
        user.age = et_age.text.toString().trim()
        AppDatabase.getAppDatabase(this@MainActivity).userDao().insertAll(user);
        Toast.makeText(this@MainActivity, "Data added", Toast.LENGTH_SHORT).show()
        user.firstName = ""
        user.lastName = ""
        user.age = ""
    }

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        super.onDestroy()
    }
}

