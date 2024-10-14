import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.example.e_commerce_iti.R
import com.example.e_commerce_iti.ui.theme._navigation.Screens
import com.example.e_commerce_iti.ui.theme.authentication.FirebaseAuthManager
import com.example.e_commerce_iti.ui.theme.home.MyLottieAnimation
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.launch

@Preview(showSystemUi = true)
@Composable
fun EnhancedLoginScreen(

) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val emailRegex = remember { Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$") }
    val passwordRegex = remember { Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}\$") }

    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("login_animation.json"))
    val progress by animateLottieCompositionAsState(composition)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF6200EE), Color(0xFF3700B3))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              //MyLottieAnimation()

                Text(
                    "Welcome Back",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        errorMessage = null
                    },
                    label = { Text("Email", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White.copy(alpha = 0.7f),
                        disabledIndicatorColor = Color.Gray,
                        errorIndicatorColor = Color.Red
                    ),
                    isError = !emailRegex.matches(email) && email.isNotEmpty(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = null
                    },
                    label = { Text("Password", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                painter = painterResource(
                                    id = if (isPasswordVisible) R.drawable.eyevisable_onn else R.drawable.eyevisable_off
                                ),
                                contentDescription = "Password Visibility",  // Move this line outside of the painterResource call
                                tint = Color.White
                            )
                        }

                    },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White.copy(alpha = 0.7f),
                        disabledIndicatorColor = Color.Gray,
                        errorIndicatorColor = Color.Red
                    ),
                    isError = !passwordRegex.matches(password) && password.isNotEmpty()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (emailRegex.matches(email) && passwordRegex.matches(password)) {
                            scope.launch {
                                isLoading = true
                                FirebaseAuthManager.login(email, password) { success, error ->
                                    isLoading = false
                                    if (success) {
                                        /*controller.navigate(Screens.Home.route)*/
                                    } else {
                                        errorMessage = error
                                    }
                                }
                            }
                        } else {
                            errorMessage = "Please enter a valid email and password"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color(0xFF6200EE))
                    } else {
                        Text("LOGIN", color = Color(0xFF6200EE), fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        scope.launch {
                        /*    isLoading = true
                            signInWithGoogle(googleSignInClient, onSignInSuccess) { error ->
                                isLoading = false
                                errorMessage = error
                            }*/
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color(0xFF6200EE))
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Google Icon",
                                tint = Color(0xFF6200EE)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Sign in with Google", color = Color(0xFF6200EE), fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = { /*controller.navigate(Screens.Signup.route*/ },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("New user? Register Now", color = Color.White)
                }

                AnimatedVisibility(visible = errorMessage != null) {
                    errorMessage?.let {
                        Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }
        }
    }
}

fun signInWithGoogle(googleSignInClient: GoogleSignInClient, launcher: ActivityResultLauncher<Intent>) {
    val signInIntent = googleSignInClient.signInIntent
    launcher.launch(signInIntent)
}
