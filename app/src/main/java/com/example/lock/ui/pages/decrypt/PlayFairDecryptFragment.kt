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


/*
class PlayFairDecryptFragment : Fragment() {
    private lateinit var etMessage: EditText
    private lateinit var etKey: EditText
    private lateinit var resultTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_play_fair_decrypt, container, false)

        etMessage = view.findViewById(R.id.et_message)
        etKey = view.findViewById(R.id.et_key)
        resultTextView = view.findViewById(R.id.resulttext)

        view.findViewById<Button>(R.id.ShowResults_button).setOnClickListener {
            decryptMessage()
        }

        return view
    }

    private fun decryptMessage() {
        val message = etMessage.text.toString().toUpperCase().replace("\\s".toRegex(), "")
        val key = etKey.text.toString().toUpperCase().replace("\\s".toRegex(), "")

        val matrix = generateKeyMatrix(key)

        val messagePairs = getPairs(message)

        val decryptedMessage = StringBuilder()
        for (pair in messagePairs) {
            val row1 = getRow(matrix, pair[0])
            val col1 = getCol(matrix, pair[0])
            val row2 = getRow(matrix, pair[1])
            val col2 = getCol(matrix, pair[1])

            if (row1 == row2) {
                // Same row
                decryptedMessage.append(matrix[row1][(col1 + 4 - 1) % 5])
                decryptedMessage.append(matrix[row2][(col2 + 4 - 1) % 5])
            } else if (col1 == col2) {
                // Same column
                decryptedMessage.append(matrix[(row1 + 4 - 1) % 5][col1])
                decryptedMessage.append(matrix[(row2 + 4 - 1) % 5][col2])
            } else {
                // Rectangle rule
                decryptedMessage.append(matrix[row1][col2])
                decryptedMessage.append(matrix[row2][col1])
            }
        }

        resultTextView.text = decryptedMessage.toString()
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

    private fun generateKeyMatrix(key: String): Array<CharArray> {
        val keyClean = key.replace("J", "I")
        val alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ"
        val keySet = keyClean.toSet()
        val alphabetSet = alphabet.toSet()
        val remainingLetters = alphabetSet.minus(keySet)

        val matrix = Array(5) { CharArray(5) }

        var row = 0
        var col = 0

        // Fill the key letters
        for (letter in keyClean) {
            matrix[row][col] = letter
            col++
            if (col == 5) {
                col = 0
                row++
            }
        }

        // Fill the remaining letters
        for (letter in remainingLetters) {
            matrix[row][col] = letter
            col++
            if (col == 5) {
                col = 0
                row++
            }
        }

        return matrix
    }
}*/
class PlayFairDecryptFragment : Fragment() {

    private lateinit var messageEditText: EditText
    private lateinit var keyEditText: EditText
    private lateinit var resultTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_play_fair_decrypt, container, false)

        messageEditText = view.findViewById(R.id.et_message)
        keyEditText = view.findViewById(R.id.et_key)
        resultTextView = view.findViewById(R.id.resulttext)
        val showResultsButton: Button = view.findViewById(R.id.ShowResults_button)

        showResultsButton.setOnClickListener {
            val message = messageEditText.text.toString()
            val key = keyEditText.text.toString()
            val decryptedText = decryptMessage(message, key)
            resultTextView.text = decryptedText
        }

        return view
    }

    private fun decryptMessage(message: String, key: String): String {
        val decryptedText = StringBuilder()
        val playFairMatrix = generatePlayFairMatrix(key)
        val cleanedMessage = message.filter { it.isLetter() }
        var index = 0

        while (index < cleanedMessage.length) {
            val char1 = cleanedMessage[index]
            var char2: Char? = null

            // check if we have a pair of characters to decrypt
            if (index < cleanedMessage.length - 1) {
                char2 = cleanedMessage[index + 1]
            }

            var (row1, col1) = findCharInMatrix(playFairMatrix, char1)
            var (row2, col2) = findCharInMatrix(playFairMatrix, char2 ?: 'X')

            if (row1 == row2) {
                // shift columns left
                col1 = (col1 - 1 + 5) % 5
                col2 = (col2 - 1 + 5) % 5
            } else if (col1 == col2) {
                // shift rows up
                row1 = (row1 - 1 + 5) % 5
                row2 = (row2 - 1 + 5) % 5
            } else {
                // swap columns
                val temp = col1
                col1 = col2
                col2 = temp
            }

            decryptedText.append(playFairMatrix[row1][col1])
            if (char2 != null) {
                decryptedText.append(playFairMatrix[row2][col2])
            }

            index += 2
        }

        return decryptedText.toString()
    }

    private fun generatePlayFairMatrix(key: String): Array<CharArray> {
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()
        val keyChars = key.toUpperCase().filter { it.isLetter() }.toCharArray()
        val matrix = Array(5) { CharArray(5) }
        val usedChars = mutableListOf<Char>()
        var index = 0

        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                val keyChar = if (index < keyChars.size) {
                    keyChars[index]
                } else {
                    var newChar = alphabet.first { !usedChars.contains(it) }
                    usedChars.add(newChar)
                    newChar
                }

                matrix[row][col] = keyChar
                index++
            }
        }

        return matrix
    }

    private fun findCharInMatrix(matrix: Array<CharArray>, char: Char): Pair<Int, Int> {
        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                if (matrix[row][col] == char) {
                    return Pair(row, col)
                }
            }
        }
        return Pair(0, 0)
    }
}
