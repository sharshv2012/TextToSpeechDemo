package com.example.texttospeechdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.texttospeechdemo.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts : TextToSpeech? = null
    private var binding : ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        tts = TextToSpeech(this , this)
        binding?.btnSpeak?.setOnClickListener{ view ->
            if(binding?.etEnteredText?.text!!.isEmpty()){
                Toast.makeText(this ,
                "Enter A Text To Speak" , Toast.LENGTH_SHORT)
                    .show()
            }else{
                speakout(binding?.etEnteredText?.text.toString())
            }

        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS" , "the language specified is not supported!")
            }
        }else{
            Log.e("TTS" , "initialization failed!")
        }
    }
    private fun speakout(text : String){
        tts?.speak(text , TextToSpeech.QUEUE_FLUSH , null , "")
        // if we use queue add it stacks the result by the times button is pressed in queue flush if button is pressed multiple times the next speech will override the previous speech.
    }

    override fun onDestroy() {
        super.onDestroy()
        if(tts != null){
            tts?.stop()
            tts?.shutdown()
        }
        binding = null
    }
}