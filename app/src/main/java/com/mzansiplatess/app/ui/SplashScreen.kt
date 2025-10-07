package com.mzansiplatess.app.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mzansiplatess.app.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    var startAnimation by remember { mutableStateOf(false) }
    val scale = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.6f,
        animationSpec = tween(durationMillis = 800, easing = LinearEasing)
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(2000)
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .scale(scale.value)
                    .shadow(8.dp, shape = CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Mzansi Platess",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Discover local food & recipes",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}


