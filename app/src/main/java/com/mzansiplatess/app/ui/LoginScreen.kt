package com.mzansiplatess.app.ui

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.mzansiplatess.app.R

@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) } // 0 = Sign In, 1 = Sign Up
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val activity = context as Activity
    val auth = FirebaseAuth.getInstance()

    // --- Google Sign-In Launcher ---
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            account?.idToken?.let { idToken ->
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener(activity) { taskResult ->
                    if (taskResult.isSuccessful) {
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        errorMessage = "Google Sign-In failed: ${taskResult.exception?.message}"
                    }
                }
            }
        } catch (e: ApiException) {
            errorMessage = "Google sign-in error: ${e.message}"
        }
    }

    fun launchGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val client = GoogleSignIn.getClient(context, gso)
        launcher.launch(client.signInIntent)
    }

    fun signInWithEmailPassword() {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        errorMessage = "Sign in failed: ${task.exception?.message}"
                    }
                }
        } else {
            errorMessage = "Please enter email and password"
        }
    }

    fun signUpWithEmailPassword() {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        errorMessage = "Sign up failed: ${task.exception?.message}"
                    }
                }
        } else {
            errorMessage = "Please enter email and password"
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Logo
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // App name
            Text(
                text = "MzansiPlates",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE65100)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Discover the best of South African cuisine",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Card
            Card(
                modifier = Modifier.fillMaxWidth(0.9f),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (selectedTab == 0) "Sign In" else "Sign Up",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Tab toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { selectedTab = 0 },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedTab == 0) Color.LightGray else Color.White
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) { Text("Sign In", color = Color.Black) }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = { selectedTab = 1 },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedTab == 1) Color.LightGray else Color.White
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) { Text("Sign Up", color = Color.Black) }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Email field
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Password field
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Orange button (Sign In or Sign Up)
                    Button(
                        onClick = {
                            if (selectedTab == 0) {
                                signInWithEmailPassword()
                            } else {
                                signUpWithEmailPassword()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F00)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            if (selectedTab == 0) "Sign In" else "Sign Up",
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(color = Color.LightGray)
                    Spacer(modifier = Modifier.height(12.dp))

                    Text("OR CONTINUE WITH", fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))

                    // Google Sign-In Button
                    Button(
                        onClick = { launchGoogleSignIn() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_logo), // Replace with Google logo
                            contentDescription = "Google",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Google", color = Color.Black)
                    }

                    errorMessage?.let {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(it, color = Color.Red, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
