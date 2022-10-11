package com.example.flashcardcodepath

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    lateinit var flashcardDatabase: FlashcardDatabase
    var allFlashcards = mutableListOf<Flashcard>()

    var currCardDisplayIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flashcardDatabase = FlashcardDatabase(this)
        allFlashcards= flashcardDatabase.getAllCards().toMutableList()

        val flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
        val flashcardAnswer = findViewById<TextView>(R.id.flashcard_answer)

        if(allFlashcards.isNotEmpty()){
            flashcardQuestion.text = allFlashcards[0].question
            flashcardAnswer.text = allFlashcards[0].answer
        }

        flashcardQuestion.setOnClickListener {
            flashcardAnswer.visibility = View.VISIBLE
            flashcardQuestion.visibility = View.INVISIBLE

            Snackbar.make(flashcardQuestion,"Question button was clicked", Snackbar.LENGTH_SHORT).show()
            Log.i("JG", "Question button was clicked")

        }
        flashcardAnswer.setOnClickListener{
            flashcardAnswer.visibility = View.INVISIBLE
            flashcardQuestion.visibility = View.VISIBLE
        }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->

            val data: Intent? = result.data

            if (data != null){
                val questionString = data.getStringExtra("QUESTION_KEY")
                val answerString = data.getStringExtra("ANSWER_KEY")

                flashcardQuestion.text = questionString
                flashcardAnswer.text = answerString

                Log.i("JG: MainActivity", "question: $questionString")
                Log.i("JG: MainActivity", "answer: $answerString")

                if(!questionString.isNullOrEmpty() && !answerString.isNullOrEmpty()){
                    flashcardDatabase.insertCard(Flashcard(questionString, answerString))
                    allFlashcards= flashcardDatabase.getAllCards().toMutableList()

                }


            }
            else{
                Log.i("JG: MainActivity", "Returned null data from AddCardActivity")
            }
        }

        val addQuestionButton = findViewById<ImageView>(R.id.add_question_button)
        addQuestionButton.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            resultLauncher.launch(intent)

        }

        val nextButton = findViewById<ImageView>(R.id.flashcard_next_button)
        nextButton.setOnClickListener {
            if(allFlashcards.isEmpty()){
                //early return so that the rest of the code doesn't execute
                return@setOnClickListener
            }

            currCardDisplayIndex++

            if(currCardDisplayIndex >= allFlashcards.size){
                //go back to beginning
                currCardDisplayIndex = 0
            }

            allFlashcards = flashcardDatabase.getAllCards().toMutableList()

            val question = allFlashcards[currCardDisplayIndex].question
            val answer = allFlashcards[currCardDisplayIndex].answer

            flashcardQuestion.text = question
            flashcardAnswer.text = answer

        }
        //val editButton = findViewById<ImageView>(R.id.flashcard_edit_button)
        //editButton.setOnClickListener{

        //}


    }


}