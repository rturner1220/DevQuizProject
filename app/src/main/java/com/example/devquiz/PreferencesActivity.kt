package com.example.devquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * Preferences screen (M05): UI only, no persistence yet.
 * In M06 we will save these values using SharedPreferences (or DataStore).
 */
class PreferencesActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { inner ->
                    PreferencesScreen(modifier = Modifier.padding(inner))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // Difficulty dropdown
    val levels = listOf("Basic", "Intermediate", "Advanced")
    var levelExpanded by remember { mutableStateOf(false) }
    var defaultLevel by remember { mutableStateOf(levels.first()) }

    // Number of questions (you can keep 5/10 only if you prefer)
    val counts = listOf("5", "10", "15", "20")
    var countExpanded by remember { mutableStateOf(false) }
    var defaultCount by remember { mutableStateOf("10") }

    var showExplanations by remember { mutableStateOf(false) }
    var shuffleQuestions by remember { mutableStateOf(true) }
    var darkTheme by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Preferences", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        // Default difficulty
        Text("Default difficulty")
        ExposedDropdownMenuBox(
            expanded = levelExpanded,
            onExpandedChange = { levelExpanded = !levelExpanded }
        ) {
            OutlinedTextField(
                value = defaultLevel,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select difficulty") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            // Use DropdownMenu (not ExposedDropdownMenu)
            DropdownMenu(
                expanded = levelExpanded,
                onDismissRequest = { levelExpanded = false }
            ) {
                levels.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = { defaultLevel = it; levelExpanded = false }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Default number of questions
        Text("Default number of questions")
        ExposedDropdownMenuBox(
            expanded = countExpanded,
            onExpandedChange = { countExpanded = !countExpanded }
        ) {
            OutlinedTextField(
                value = defaultCount,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select count") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            DropdownMenu(
                expanded = countExpanded,
                onDismissRequest = { countExpanded = false }
            ) {
                counts.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = { defaultCount = it; countExpanded = false }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Show explanations
        RowSwitch(
            title = "Show explanations at the end",
            checked = showExplanations,
            onCheckedChange = { showExplanations = it }
        )

        Spacer(Modifier.height(8.dp))

        // Shuffle questions
        RowSwitch(
            title = "Shuffle questions",
            checked = shuffleQuestions,
            onCheckedChange = { shuffleQuestions = it }
        )

        Spacer(Modifier.height(8.dp))

        // Theme
        RowSwitch(
            title = "Dark theme (preview only for now)",
            checked = darkTheme,
            onCheckedChange = { darkTheme = it }
        )

        Spacer(Modifier.height(24.dp))

        // Back button (outside of any dropdowns)
        Button(
            onClick = {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Home")
        }

        Spacer(Modifier.height(8.dp))
        Text(
            text = "Note: In Module 6 these preferences will be persisted and loaded automatically.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun RowSwitch(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            thumbContent = null,
            colors = SwitchDefaults.colors()
        )
    }
}
