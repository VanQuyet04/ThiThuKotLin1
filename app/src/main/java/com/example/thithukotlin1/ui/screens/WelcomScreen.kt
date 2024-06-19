package com.example.thithukotlin1.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thithukotlin1.R
import com.example.thithukotlin1.ui.navigation.ROUTE_SCREEN_NAME
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.b1), contentDescription = "logo",
            modifier = Modifier.size(200.dp)
        )

        Text(
            text = "PH35419 - Trương Văn Quyết",
            fontSize = 22.sp,
            fontWeight = FontWeight(500),
            modifier = Modifier.padding(top = 15.dp)

        )
        Text(
            text = "20-06-2024",
            fontSize = 22.sp,
            fontWeight = FontWeight(500),
            modifier = Modifier.padding(top = 5.dp)

        )
        Button(
            onClick = {
                navController.navigate(ROUTE_SCREEN_NAME.HOME.name) {
                    popUpTo(ROUTE_SCREEN_NAME.WELCOME.name) {
                        inclusive = true
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(Color.Blue)
        ) {
            Text(text = "Ok")
        }

//        LaunchedEffect(key1 = Unit) {
//            delay(2000L)
//            navController.navigate(ROUTE_SCREEN_NAME.HOME.name) {
//                popUpTo(ROUTE_SCREEN_NAME.WELCOME.name) {
//                    inclusive = true
//                }
//            }
//        }


    }

}