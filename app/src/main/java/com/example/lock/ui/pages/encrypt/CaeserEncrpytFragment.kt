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


class CaeserEncrpytFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_caeser_encrpyt, container, false)

        // Get references to UI elements
        val messageEditText = view.findViewById<EditText>(R.id.et_message)
        val keyEditText = view.findViewById<EditText>(R.id.et_key)
        val resultTextView = view.findViewById<TextView>(R.id.resulttext)

        // Set a click listener on the "Encrypt" button
        view.findViewById<Button>(R.id.ShowResults_button).setOnClickListener {
            // Get the message and key values entered by the user
            val message = messageEditText.text.toString()
            val key = keyEditText.text.toString().toInt()

            // Encrypt the message using Caesar Cipher
            val encryptedMessage = encrypt(message, key)

            // Set the encrypted message in the result text view
            resultTextView.text = encryptedMessage
        }

        return view
    }
    fun encrypt(message: String, key: Int): String {
        val sb = StringBuilder()

        for (i in message.indices) {
            var c = message[i]

            // Encrypt uppercase characters
            if (c in 'A'..'Z') {
                c = ((c.toInt() - 'A'.toInt() + key) % 26 + 'A'.toInt()).toChar()
            }
            // Encrypt lowercase characters
            else if (c in 'a'..'z') {
                c = ((c.toInt() - 'a'.toInt() + key) % 26 + 'a'.toInt()).toChar()
            }

            sb.append(c)
        }

        return sb.toString()
    }

}


