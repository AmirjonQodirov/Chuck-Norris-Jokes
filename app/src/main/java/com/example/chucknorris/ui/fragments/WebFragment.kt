package com.example.chucknorris.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.chucknorris.R
import kotlinx.android.synthetic.main.fragment_web.*
import android.app.Activity

import android.content.SharedPreferences





class WebFragment : Fragment() {

    var webView: WebView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_web, container, false)

        webView = view.findViewById(R.id.web)
        webView?.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl("https://www.icndb.com/api/")
        }
        return view
    }

    override fun onPause() {
        super.onPause()
        val prefs = context!!.applicationContext.getSharedPreferences(
            context!!.packageName,
            Activity.MODE_PRIVATE
        )
        val edit: SharedPreferences.Editor = prefs.edit()
        edit.putString("lastUrl", webView!!.url)
        edit.apply() // can use edit.apply() but in this case commit is better
    }

    override fun onResume() {
        super.onResume()
        if (webView != null) {
            val prefs = context!!.applicationContext.getSharedPreferences(
                context!!.packageName, Activity.MODE_PRIVATE
            )
            val s = prefs.getString("lastUrl", "")
            if (s != "") {
                webView!!.loadUrl(s!!)
            }
        }
    }


}