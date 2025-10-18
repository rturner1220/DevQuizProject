package com.example.devquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * Help screen: display-only text per assignment spec.
 * No interactivity required (scrollable text is enough).
 */
class HelpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { inner ->
                    HelpScreen(modifier = Modifier.padding(inner))
                }
            }
        }
    }
}

@Composable
fun HelpScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("Help", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        Text(
            """
            • About the app – DevQuiz is a simple multiple-choice quiz designed for developers.
            • How it works – From the Main screen you enter your name, select number of questions and difficulty, and start the quiz.
            • Preferences – You can change default difficulty, default number of questions, turn on/off explanations, and shuffle questions.
              In Module 6 these settings will be saved and automatically applied when you reopen the app.
            """.trimIndent(),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Home")
        }
    }
}
