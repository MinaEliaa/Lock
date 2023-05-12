package com.example.lock.ui.pages.decrypt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.lock.R



class CaeserDecryptFragment : Fragment() {

    private lateinit var messageEditText: EditText
    private lateinit var keyEditText: EditText
    private lateinit var resultTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_caeser_decrypt, container, false)

        // Initialize views
        messageEditText = view.findViewById(R.id.et_message)
        keyEditText = view.findViewById(R.id.et_key)
        resultTextView = view.findViewById(R.id.resulttext)

        // Set click listener for button
        val showResultsButton = view.findViewById<Button>(R.id.ShowResults_button)
        showResultsButton.setOnClickListener {
            decryptMessage()
        }

        return view
    }

    private fun decryptMessage() {
        val message = messageEditText.text.toString()
        val key = keyEditText.text.toString().toInt()

        val decryptedMessage = caesarDecrypt(message, key)

        resultTextView.text = decryptedMessage
    }

    private fun caesarDecrypt(message: String, key: Int): String {
        val result = StringBuilder()
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

        for (char in message) {
            if (char.isLetter()) {
                val oldPosition = alphabet.indexOf(char.toUpperCase())
                val newPosition = (oldPosition - key) % alphabet.length

                result.append(alphabet[newPosition])
            } else {
                result.append(char)
            }
        }

        return result.toString()
    }

}