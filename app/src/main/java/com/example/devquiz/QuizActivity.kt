package com.example.devquiz

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Receive data from MainActivity
        val name = intent.getStringExtra("playerName") ?: "Player"
        val count = intent.getIntExtra("questionCount", 10)
        val level = intent.getStringExtra("difficulty") ?: "Basic"

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    QuizScreen(name, count, level)
                }
            }
        }
    }
}

@Composable
fun QuizScreen(name: String, count: Int, level: String) {
    val context = LocalContext.current

    Column(modifier = Modifier.padding(24.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Text button avoids adding the icons dependency
            TextButton(onClick = { (context as? Activity)?.finish() }) {
                Text("Back")
            }
            Spacer(Modifier.width(12.dp))
            Text(
                text = "Question 1 of $count",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Spacer(Modifier.height(12.dp))

        Text("Welcome $name. First question at $level level.")
        Spacer(Modifier.height(12.dp))
        Text("Sample question text will appear here.")
    }
}
