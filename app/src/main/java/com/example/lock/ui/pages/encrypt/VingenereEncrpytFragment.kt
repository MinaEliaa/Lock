package com.example.lock.ui.pages.encrypt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.lock.R


class VingenereEncrpytFragment : Fragment() {



    private lateinit var messageEditText: EditText
    private lateinit var keyEditText: EditText
    private lateinit var resultTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_play_fair_encrypt, container, false)

        // Initialize views
        messageEditText = view.findViewById(R.id.et_message)
        keyEditText = view.findViewById(R.id.et_key)
        resultTextView = view.findViewById(R.id.resulttext)

        // Set click listener for button
        val showResultsButton = view.findViewById<Button>(R.id.ShowResults_button)
        showResultsButton.setOnClickListener {
            encryptMessage()
        }

        return view
    }

    private fun encryptMessage() {
        val message = messageEditText.text.toString()
        val key = keyEditText.text.toString()

        val encryptedMessage = vigenereEncrypt(message, key)

        resultTextView.text = encryptedMessage
    }

    private fun vigenereEncrypt(message: String, key: String): String {
        val result = StringBuilder()

        val messageUpperCase = message.toUpperCase()
        val keyUpperCase = key.toUpperCase()

        val messageWithoutSpaces = messageUpperCase.replace(" ", "")
        val keyWithoutSpaces = keyUpperCase.replace(" ", "")

        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

        var keyIndex = 0

        for (char in messageWithoutSpaces) {
            if (char.isLetter()) {
                val messageCharIndex = alphabet.indexOf(char)
                val keyCharIndex = alphabet.indexOf(keyWithoutSpaces[keyIndex])

                val newIndex = (messageCharIndex + keyCharIndex) % alphabet.length
                val newChar = alphabet[newIndex]

                result.append(newChar)

                keyIndex = (keyIndex + 1) % keyWithoutSpaces.length
            } else {
                result.append(char)
            }
        }

        return result.toString()
    }

}