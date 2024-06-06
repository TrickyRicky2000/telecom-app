package com.example.telecomapp

//import androidx.compose.material3.icons.Icons
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.telecomapp.ui.theme.TelecomAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelecomAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoApp()
                }
            }
        }
    }
}

@Composable
fun TodoApp() {
    var taskText by remember { mutableStateOf("") }
    var tasks by remember { mutableStateOf(listOf<Task>()) }



    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {

            BasicTextField(
                value = taskText,
                onValueChange = { taskText = it },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (taskText.isNotEmpty()) {
                            tasks = tasks + Task(taskText, false)
                            taskText = ""
                        }
                    }
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .background(Color.Gray.copy(alpha = 0.1f))
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (taskText.isNotEmpty()) {
                    tasks = tasks + Task(taskText, false)
                    taskText = ""
                }
            }) {
                Text("Add")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TaskList(
            tasks = tasks,
            onTaskCheckedChange = { index, isChecked ->
                tasks = tasks.toMutableList().also { it[index] = it[index].copy(isCompleted = isChecked) }
            },
            onTaskDelete = { index ->
                tasks = tasks.toMutableList().also { it.removeAt(index) }
            }
        )
    }
}

@Composable
fun TaskList(
    tasks: List<Task>,
    onTaskCheckedChange: (Int, Boolean) -> Unit,
    onTaskDelete: (Int) -> Unit
) {
    Column {
        tasks.forEachIndexed { index, task ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Checkbox(
                        checked = task.isCompleted,
                        onCheckedChange = { onTaskCheckedChange(index, it) }
                    )
                    Text(
                        text = task.text,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .weight(1f)
                    )
                }
                Button(onClick = { onTaskDelete(index) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Task")
                }
            }
            Divider()
        }
    }
}

data class Task(val text: String, val isCompleted: Boolean)

@Preview(showBackground = true)
@Composable
fun TodoAppPreview() {
    TelecomAppTheme {
        TodoApp()
    }
}
