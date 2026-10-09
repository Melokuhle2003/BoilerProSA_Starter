package za.co.boilerprosa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { BoilerProSAApp() }
    }
}

@Composable
fun BoilerProSAApp() {
    MaterialTheme {
        val navController = rememberNavController()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("BoilerPro SA", fontWeight = FontWeight.Bold) }
                )
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(padding)
            ) {
                composable("home") {
                    HomeScreen(
                        onCalculator = { navController.navigate("bend") },
                        onTraining = { navController.navigate("training") }
                    )
                }
                composable("bend") { BendLineCalculatorScreen() }
                composable("training") { TrainingScreen() }
            }
        }
    }
}

@Composable
fun HomeScreen(
    onCalculator: () -> Unit,
    onTraining: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Boilermaking Trade & Fabrication", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Learn, calculate and practise South African boilermaking trade skills.",
            style = MaterialTheme.typography.bodyLarge
        )

        Button(
            onClick = onCalculator,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Bend-Line Calculator")
        }

        OutlinedButton(
            onClick = onTraining,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Trade-Test Training")
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Coming next", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text("• Cone and transition calculations")
                Text("• Pipe and plate calculations")
                Text("• Technical drawings")
                Text("• Practice tests and scoring")
            }
        }
    }
}

@Composable
fun BendLineCalculatorScreen() {
    var bendLines by remember { mutableStateOf("") }

    val divisions = bendLines.toIntOrNull()
    val result = if (divisions != null && divisions > 0) {
        360.0 / divisions
    } else null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Bend-Line Calculator", style = MaterialTheme.typography.headlineSmall)
        Text("Equal divisions around a 360° development")

        OutlinedTextField(
            value = bendLines,
            onValueChange = { bendLines = it.filter(Char::isDigit) },
            label = { Text("Number of bend lines") },
            placeholder = { Text("Example: 12") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (result != null) {
            val rounded = round(result * 100.0) / 100.0
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("Calculation", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("360° ÷ $divisions = $rounded°")
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "$rounded° per bend-line division",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            Text("Enter a positive number of bend lines.")
        }

        HorizontalDivider()

        Text("Examples", fontWeight = FontWeight.Bold)
        Text("12 bend lines → 360° ÷ 12 = 30°")
        Text("24 bend lines → 360° ÷ 24 = 15°")
    }
}

@Composable
fun TrainingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Trade-Test Training", style = MaterialTheme.typography.headlineSmall)
        Text("The training module is ready for expansion.")
        Text("Next: question bank, multiple-choice tests, scoring and progress tracking.")
    }
}
