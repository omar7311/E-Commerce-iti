import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

enum class SheetContent {
    ABOUT,
    CONTACT
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallableBottomSheet(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        sheetState.show()
    }

    ModalBottomSheet(
        onDismissRequest = {
            coroutineScope.launch {
                sheetState.hide()
                onDismiss()
            }
        },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            content()
        }
    }
}

@Composable
fun BottomSheetContent(sheetContent: SheetContent) {
    when (sheetContent) {
        SheetContent.ABOUT -> AboutContent()
        SheetContent.CONTACT -> ContactUsContent()
    }
}

@Composable
fun AboutContent() {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFFE6E6FA), Color(0xFFE6E6FA), Color(0xFFE6E6FA), Color(0xFFE6E6FA), Color(0xFFAFEEEE))
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = gradientBrush)
            .padding(24.dp)
    ) {
        Text(
            "About Us",
            style = MaterialTheme.typography.headlineLarge.copy(
                color = Color(0xFF1A237E),
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "We are the android-sv24-Team1 dedicated to creating amazing apps!",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFF283593)
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Team Members:",
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color(0xFF303F9F),
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        TeamMember("Ahmed Hassan", "Android Developer")
        TeamMember("Omar Ahmed", "Android Developer")
        TeamMember("Mostafa Gamal", "Android Developer")
    }
}

@Composable
fun TeamMember(name: String, role: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(Color(0xFF3F51B5), shape = RoundedCornerShape(50))
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                name,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color(0xFF3F51B5),
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                role,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF5C6BC0)
                )
            )
        }
    }
}

@Composable
fun ContactUsContent() {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFFE6E6FA), Color(0xFFE6E6FA), Color(0xFFE6E6FA), Color(0xFFE6E6FA), Color(0xFFE6E6FA))
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = gradientBrush)
            .padding(24.dp)
    ) {
        Text(
            "Contact Us",
            style = MaterialTheme.typography.headlineLarge.copy(
                color = Color(0xFF3E2723),
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "We'd love to hear from you!",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFF4E342E)
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        ContactInfo("Email", "ahmedhassan@fci.helwan.edu.eg")
        ContactInfo("Phone", "01099111313")
        ContactInfo("Address", "Giza/Egypt")
    }
}

@Composable
fun ContactInfo(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            label,
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color(0xFF3E2723),
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFF4E342E)
            )
        )
    }
}
