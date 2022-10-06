package com.example.flashcardcodepath

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.EditText
import android.widget.ImageView

class AddCardActivity() : AppCompatActivity(), Parcelable {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
    }

    val questionEditText = findViewById<EditText>(R.id.flashcard_question_edittext)
    val answerEditText = findViewById<EditText>(R.id.flashcard_answer_edittext)


    val saveButton = findViewById<ImageView>(R.id.flashcard_save_button)

    constructor(parcel: Parcel) : this() {

    saveButton.setOnClickListener {
        val questionString = questionEditText.text.toString()
        val answerString = answerEditText.text.toString()
        val data = Intent()
        val putExtra = data.putExtra("QUESTION_KEY", questionString)
        data.putExtra("ANSWER_KEY", answerString)

        setResult(RESULT_OK, data)
        finish()
        }
    }

    //val cancelButton = findViewById<ImageView>(R.id.flashcard_cancel_button)
    //cancelButton.setonClickListener {
     //   finish()
    //}

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AddCardActivity> {
        override fun createFromParcel(parcel: Parcel): AddCardActivity {
            return AddCardActivity(parcel)
        }

        override fun newArray(size: Int): Array<AddCardActivity?> {
            return arrayOfNulls(size)
        }
    }


}