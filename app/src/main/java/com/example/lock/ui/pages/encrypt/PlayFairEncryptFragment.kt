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



    class PlayFairEncryptFragment : Fragment() {

        private lateinit var etMessage: EditText
        private lateinit var etKey: EditText
        private lateinit var resultText: TextView
        private lateinit var showResultsButton: Button

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            val view = inflater.inflate(R.layout.fragment_play_fair_encrypt, container, false)

            etMessage = view.findViewById(R.id.et_message)
            etKey = view.findViewById(R.id.et_key)
            resultText = view.findViewById(R.id.resulttext)
            showResultsButton = view.findViewById(R.id.ShowResults_button)

            showResultsButton.setOnClickListener {
                val message = etMessage.text.toString().toUpperCase()
                val key = etKey.text.toString().toUpperCase()
                val encryptedMessage = encryptMessage(message, key)
                resultText.text = encryptedMessage
            }

            return view
        }

        private fun encryptMessage(message: String, key: String): String {
            // Implement the Playfair encryption algorithm
            val letters = "ABCDEFGHIKLMNOPQRSTUVWXYZ"
            var keyMatrix = Array(5) { CharArray(5) }
            var messageClean = message.replace(" ", "")
            var messagePairs = getPairs(messageClean)
            var encryptedText = ""

            // Generate the key matrix
            var keyClean = key.replace("J", "I")
            keyClean += letters
            keyClean = keyClean.replace(" ", "")
            keyClean = keyClean.toUpperCase()
            keyClean = keyClean.toCharArray().distinct().joinToString("")
            var keyList = keyClean.toList()
            var keyListIndex = 0

            for (i in 0..4) {
                for (j in 0..4) {
                    keyMatrix[i][j] = keyList[keyListIndex]
                    keyListIndex++
                }
            }

            // Encrypt the message pairs
            for (pair in messagePairs) {
                val row1: Int
                val row2: Int
                val col1: Int
                val col2: Int

                val letter1 = pair[0]
                val letter2 = pair[1]

                row1 = getRow(keyMatrix, letter1)
                col1 = getCol(keyMatrix, letter1)
                row2 = getRow(keyMatrix, letter2)
                col2 = getCol(keyMatrix, letter2)

                if (row1 == row2) {
                    encryptedText += keyMatrix[row1][(col1 + 1) % 5]
                    encryptedText += keyMatrix[row2][(col2 + 1) % 5]
                } else if (col1 == col2) {
                    encryptedText += keyMatrix[(row1 + 1) % 5][col1]
                    encryptedText += keyMatrix[(row2 + 1) % 5][col2]
                } else {
                    encryptedText += keyMatrix[row1][col2]
                    encryptedText += keyMatrix[row2][col1]
                }
            }

            return encryptedText
        }

        private fun getPairs(message: String): List<String> {
            var messageClean = message
            var messagePairs = mutableListOf<String>()
            if (messageClean.length % 2 != 0) {
                messageClean += "X"
            }
            for (i in 0 until messageClean.length step 2) {
                val pair = messageClean.substring(i, i + 2)
                messagePairs.add(pair)
            }
            return messagePairs
        }

        private fun getRow(matrix: Array<CharArray>, letter: Char): Int {
            for (i in matrix.indices) {
                for (j in matrix[i].indices) {
                    if (matrix[i][j] == letter) {
                        return i
                    }
                }
            }
            return -1
        }

        private fun getCol(matrix: Array<CharArray>, letter: Char): Int {
            for (i in matrix.indices) {
                for (j in matrix[i].indices) {
                    if (matrix[i][j] == letter) {
                        return j
                    }
                }
            }
            return -1
        }
    }