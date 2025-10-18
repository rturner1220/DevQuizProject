package com.example.devquiz

import android.content.Intent
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * Help screen: display-only text per assignment spec.
 * No interactivity required (scrollable text is enough).
 */
class HelpActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val context = LocalContext.current
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Help") },
                            navigationIcon = {
                                TextButton(onClick = { (context as? Activity)?.finish() }) {
                                    Text("Back")
                                }
                            }
                        )
                    }
                ) { inner ->
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
        Text("About DevQuiz", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        Text(
            """
            • What it is – DevQuiz is a simple multiple-choice quiz for developers.
            • How it works – On the Main screen, enter your name, choose a difficulty 
              (Basic / Intermediate / Advanced) and the number of questions (default 5), 
              then tap “Start Quiz”.
            • Preferences – From the Preferences screen you can set default difficulty 
              and default number of questions, and toggle “Show explanations” and 
              “Shuffle questions”. These settings are applied when you start a new quiz.
            • Quiz flow – Each quiz presents 5 questions for the selected difficulty. 
              After answering, a result screen shows your score (e.g., “You answered 3 of 5”). 
              You can finish (go back Home) or try again.
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
