package com.example.devquiz

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width

// ----- Simple question model -----
data class QuizQuestion(
    val text: String,
    val options: List<String>,
    val answerIndex: Int
)

// ----- Question bank: 5 per level -----
private fun questionsFor(level: String): List<QuizQuestion> {
    return when (level.lowercase()) {
        "basic" -> listOf(
            QuizQuestion(
                "What does IDE stand for?",
                listOf("Integrated Development Environment", "Internal Debug Engine", "Internet Dev Editor", "Inline Dev Executor"),
                0
            ),
            QuizQuestion(
                "Which language runs on the JVM?",
                listOf("Kotlin", "Swift", "JavaScript", "Go"),
                0
            ),
            QuizQuestion(
                "What is Git primarily used for?",
                listOf("Version control", "Image editing", "UI design", "Package hosting"),
                0
            ),
            QuizQuestion(
                "Which file holds Android app permissions?",
                listOf("AndroidManifest.xml", "build.gradle.kts", "strings.xml", "themes.xml"),
                0
            ),
            QuizQuestion(
                "What is an Activity in Android?",
                listOf("A single screen with a UI", "A database row", "A background thread", "A web service"),
                0
            )
        )
        "intermediate" -> listOf(
            QuizQuestion(
                "In Kotlin, which keyword declares a read-only variable?",
                listOf("val", "var", "let", "const fun"),
                0
            ),
            QuizQuestion(
                "Jetpack Compose state that survives recomposition is usually kept with…",
                listOf("remember { mutableStateOf(...) }", "var x = 0", "Data class only", "XML layout"),
                0
            ),
            QuizQuestion(
                "What does an Intent do in Android?",
                listOf("Requests an action from another component", "Compiles the app", "Renders Compose", "Encrypts SharedPreferences"),
                0
            ),
            QuizQuestion(
                "Which scope is recommended for UI state in Compose on Android?",
                listOf("ViewModel", "Global object", "Activity field only", "Static singleton"),
                0
            ),
            QuizQuestion(
                "Which Gradle file defines module dependencies in Kotlin DSL?",
                listOf("app/build.gradle.kts", "settings.gradle.kts", "gradle.properties", "local.properties"),
                0
            )
        )
        else -> listOf( // "advanced"
            QuizQuestion(
                "What is a cold Flow in Kotlin?",
                listOf("A stream that starts producing values when collected", "A stream that caches values for late collectors", "A stream that never completes", "A stream bound to the main thread"),
                0
            ),
            QuizQuestion(
                "Which pattern helps separate UI from business logic in Android apps?",
                listOf("MVVM", "Singleton everywhere", "Active Record", "God Activity"),
                0
            ),
            QuizQuestion(
                "What’s the main advantage of dependency injection?",
                listOf("Testability & decoupling", "Faster rendering", "Less code formatting", "No need for gradle"),
                0
            ),
            QuizQuestion(
                "In Compose, what’s the recommended way to handle expensive operations across recompositions?",
                listOf("remember / rememberSaveable", "Inline everything", "Re-run on every recomposition", "Use global mutable vars"),
                0
            ),
            QuizQuestion(
                "Which API stores key–value user settings asynchronously and is recommended over SharedPreferences?",
                listOf("DataStore", "Room", "WorkManager", "Navigation"),
                0
            )
        )
    }
}
class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Receive data from MainActivity
        val name = intent.getStringExtra("playerName") ?: "Player"
        val count = intent.getIntExtra("questionCount", 5)
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
    val all = questionsFor(level)

    // We only allow 5 questions
    val questions = remember(level) { all.take(5) }

    var index by remember { mutableStateOf(0) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var score by remember { mutableStateOf(0) }
    var finished by remember { mutableStateOf(false) }

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
                text = if (!finished) "Question ${index + 1} of ${questions.size}" else "Result",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(16.dp))

        if (!finished) {
            // Welcome / level hint only before first question
            if (index == 0) {
                Text("Welcome $name. First question at $level level.")
                Spacer(Modifier.height(12.dp))
            }

            val q = questions[index]

            Text(q.text, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(12.dp))

            // Options
            q.options.forEachIndexed { i, option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedIndex = i }
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedIndex == i,
                        onClick = { selectedIndex = i }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(option)
                }
            }

            Spacer(Modifier.height(20.dp))

            val isLast = index == questions.lastIndex
            Button(
                onClick = {
                    if (selectedIndex != null && selectedIndex == q.answerIndex) {
                        score++
                    }
                    if (isLast) {
                        finished = true
                    } else {
                        index++
                        selectedIndex = null
                    }
                },
                enabled = selectedIndex != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isLast) "Submit" else "Next")
            }
        } else {
            // Result screen
            Text(
                "You did $score of ${questions.size}",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(12.dp))
            Text("Nice work, $name!")

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { (context as? Activity)?.finish() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Finish (Home)")
            }
            Spacer(Modifier.height(10.dp))
            OutlinedButton(
                onClick = {
                    // restart same level
                    index = 0
                    score = 0
                    finished = false
                    selectedIndex = null
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Try again")
            }
        }
    }
}