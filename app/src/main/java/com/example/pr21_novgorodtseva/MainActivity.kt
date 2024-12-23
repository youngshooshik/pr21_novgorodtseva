package com.example.pr21_novgorodtseva

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pr21_novgorodtseva.ui.theme.Pr21_novgorodtsevaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pr21_novgorodtsevaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val dbHelper = remember { DatabaseHelper(context) }
    var users by remember { mutableStateOf(dbHelper.getAllUsers()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            val intent = Intent(context, UserActivity::class.java)
            intent.putExtra("userId", 0L)
            context.startActivity(intent)
        }) {
            Text("Добавить пользователя")
        }

        Spacer(modifier = Modifier.height(16.dp))

        users.forEach { user ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Имя: ${user.second.first}, Год: ${user.second.second}",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    dbHelper.deleteUser(user.first)

                    users = dbHelper.getAllUsers()
                }) {
                    Text("Удалить")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}