package edu.ucne.tasktally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.tasktally.presentation.TaskTallyApp
import edu.ucne.tasktally.ui.theme.TaskTallyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            TaskTallyTheme {
                TaskTallyApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskTallyAppPreview() {
    TaskTallyTheme {
        TaskTallyApp()
    }
}