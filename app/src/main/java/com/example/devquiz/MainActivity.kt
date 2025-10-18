package com.example.devquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Entry point activity. It renders the home screen where the user:
 * - types their name
 * - picks difficulty (basic / intermediate / advanced)
 * - navigates to Quiz, Preferences, or Help screens
 */
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // We use MaterialTheme directly for M04/M05; a custom theme is optional.
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = androidx.compose.ui.platform.LocalContext.current

    // Store user name
    var name by remember { mutableStateOf(TextFieldValue("")) }

    // Question count dropdown
    val counts = listOf("5")
    var countExpanded by remember { mutableStateOf(false) }
    var selectedCount by remember { mutableStateOf(counts.first()) }

    // Difficulty dropdown
    val levels = listOf("Basic", "Intermediate", "Advanced")
    var levelExpanded by remember { mutableStateOf(false) }
    var selectedLevel by remember { mutableStateOf(levels.first()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Welcome to DevQuiz", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Your name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))
        Text("Number of questions")

        ExposedDropdownMenuBox(
            expanded = countExpanded,
            onExpandedChange = { countExpanded = !countExpanded }
        ) {
            OutlinedTextField(
                value = selectedCount,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            // FIX: use DropdownMenu (not ExposedDropdownMenu)
            DropdownMenu(
                expanded = countExpanded,
                onDismissRequest = { countExpanded = false }
            ) {
                counts.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = { selectedCount = item; countExpanded = false }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Text("Difficulty level")

        ExposedDropdownMenuBox(
            expanded = levelExpanded,
            onExpandedChange = { levelExpanded = !levelExpanded }
        ) {
            OutlinedTextField(
                value = selectedLevel,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            DropdownMenu(
                expanded = levelExpanded,
                onDismissRequest = { levelExpanded = false }
            ) {
                levels.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = { selectedLevel = item; levelExpanded = false }
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                val i = Intent(context, QuizActivity::class.java).apply {
                    putExtra("playerName", name.text.ifBlank { "Player" })
                    putExtra("questionCount", selectedCount.toInt())
                    putExtra("difficulty", selectedLevel)
                }
                context.startActivity(i)
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Start Quiz") }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = {
                    context.startActivity(Intent(context, PreferencesActivity::class.java))
                },
                modifier = Modifier.weight(1f)
            ) { Text("Preferences") }

            OutlinedButton(
                onClick = {
                    context.startActivity(Intent(context, HelpActivity::class.java))
                },
                modifier = Modifier.weight(1f)
            ) { Text("Help") }
        }
    }
}

/**
 * Preview for Android Studio only; it doesn't run in the emulator.
 * Handy to verify layout spacing and text sizes.
 */
@Preview(showBackground = true)
@Composable
fun MainPreview() {
    MaterialTheme {
        MainScreen()
    }
}
