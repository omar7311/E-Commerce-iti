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
import com.example.e_commerce_iti.gradientBrush
import com.example.e_commerce_iti.ingredientColor1
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(androidx.compose.ui.graphics.Color.White)
            .padding(24.dp)
    ) {
        Text(
            "About Us",
            style = MaterialTheme.typography.headlineLarge.copy(
                color = ingredientColor1,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "We are the android-sv24-Team1 dedicated to creating amazing apps!",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = ingredientColor1
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Team Members:",
            style = MaterialTheme.typography.titleLarge.copy(
                color = ingredientColor1,
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
                .background(androidx.compose.ui.graphics.Color.White, shape = RoundedCornerShape(50))
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                name,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = ingredientColor1,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                role,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = ingredientColor1
                )
            )
        }
    }
}

@Composable
fun ContactUsContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(androidx.compose.ui.graphics.Color.White)
            .padding(24.dp)
    ) {
        Text(
            "Contact Us",
            style = MaterialTheme.typography.headlineLarge.copy(
                color = ingredientColor1),
                fontWeight = FontWeight.Bold
            )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "We'd love to hear from you!",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = ingredientColor1
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
                color = ingredientColor1,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = ingredientColor1
            )
        )
    }
}
