package com.example.thithukotlin1.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.thithukotlin1.ui.navigation.AppNavigation
import com.example.thithukotlin1.ui.theme.ThiThuKotlin1Theme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThiThuKotlin1Theme {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()) { innerPadding ->
                    AppNavigation(navController = rememberNavController())
                }
            }
        }
    }
}
