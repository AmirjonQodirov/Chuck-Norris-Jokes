package com.example.chucknorris.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.chucknorris.R
import com.example.chucknorris.ui.JokesViewModel
import com.example.chucknorris.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_joke.*

class JokeFragment: Fragment(R.layout.fragment_joke) {
    lateinit var viewModel: JokesViewModel
    val args: JokeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        val joke = args.joke

        joke_txt.text = joke.joke

        fab.setOnClickListener {
            viewModel.saveJoke(joke)
            Snackbar.make(view, "Joke saved successfully & marked favorite", Snackbar.LENGTH_SHORT).show()
        }
    }
}