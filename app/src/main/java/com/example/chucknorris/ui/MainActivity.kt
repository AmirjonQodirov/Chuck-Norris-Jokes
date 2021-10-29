package com.example.chucknorris.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.chucknorris.R
import com.example.chucknorris.db.ValueDatabase
import com.example.chucknorris.repository.JokesRepository
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: JokesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jokesRepository = JokesRepository(ValueDatabase(this))
        val viewModelProviderFactory = JokesViewModelProviderFactory(jokesRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(JokesViewModel::class.java)
        bottomNavigationView.setupWithNavController(jokesNavHostFragment.findNavController())

    }
}