package ru.frozenrpiest.academyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.frozenrpiest.academyapp.adapters.ItemAdapterCast
import ru.frozenrpiest.academyapp.adapters.LinearLayoutPagerManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
     }
}