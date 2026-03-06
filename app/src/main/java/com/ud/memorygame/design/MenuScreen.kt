package com.ud.memorygame.design

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ud.memorygame.R

@Composable
fun MenuScreen(
    navController: NavController
) {
    // box to layer the background image and content
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // background image
        Image(
            painter = painterResource(id = R.drawable.fondo), // make sure you have this image
            contentDescription = null, // decorative image
            contentScale = ContentScale.Crop, // make image cover the whole screen
            modifier = Modifier.fillMaxSize()
        )

        // content overlay (centered on top of background)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {

                // space between title and button
                Spacer(modifier = Modifier.height(32.dp))

                // start button
                Button(
                    onClick = {
                        // navigate to levels screen
                        navController.navigate("levels")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = "START",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}