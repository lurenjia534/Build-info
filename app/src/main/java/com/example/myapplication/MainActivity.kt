package com.example.myapplication

import android.media.MediaCodecList
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.surface
                ) {
                    AppUI(color = MaterialTheme.colorScheme.primaryContainer)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppUI(color: Color) {
        MaterialTheme {
            Surface {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(color)
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize() // 填充整个容器
                        .verticalScroll(rememberScrollState())

                ) {
                    val showCardDialog = remember{ mutableStateOf(false) } // 关于页面

                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Device info",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleLarge
                            )
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        navigationIcon = {
                            IconButton(onClick = { /*TODO*/ }) {

                            }
                        },
                        actions = {
                            IconButton(onClick = { showCardDialog.value = true }) {
                                Icon(
                                    imageVector = Icons.TwoTone.Favorite,
                                    contentDescription = null,
                                )
                            }
                        }
                    )

                    if (showCardDialog.value){
                        AlertDialog(
                            onDismissRequest = { showCardDialog.value = false },
                            title = { Text("About") },
                            text = { Text(text = "Maintainer: @lurenjia534", style = MaterialTheme.typography.titleSmall) },
                            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primaryContainer,
                            textContentColor = MaterialTheme.colorScheme.primaryContainer,
                            confirmButton = {
                                TextButton(onClick = { showCardDialog.value = false }) {
                                    Text("Ok")
                                }

                            })
                    }

                    val androidVersion = Build.VERSION.RELEASE // Android版本
                    val sdkLevel = Build.VERSION.SDK_INT // SDK等级
                    val androidID = Build.ID // 设备ID
                    val brand = Build.BRAND //与产品/硬件相关联的消费者可见品牌
                    val board = Build.BOARD // 主板型号
                    val manufacturer = Build.MANUFACTURER // 制造商
                    val model = Build.MODEL // 设备名称
                    val fingerprint = Build.FINGERPRINT //唯一标识此构建的字符串
                    val product = Build.PRODUCT // 整体产品的名称。
                    val buildType = Build.TYPE // 构建类型
                    val sku = Build.SKU // SKU 这里可能指设备唯一代号 比如Redmi Note 12 Turbo 代号: Marble

                    OutlinedCard(
                        onClick = { /* 点击事件处理 */ },
                        modifier = Modifier
                            .padding(16.dp) // 外部间距
                            .fillMaxWidth() // 宽度填充父容器，但不是高度
                            .wrapContentHeight(), // 高度包裹内容
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.outlinedCardColors(MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(24.dp) // 内部间距
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.Start, // 水平对其方式
                            verticalArrangement = Arrangement.spacedBy(8.dp) // 文本之间的间隔
                        ) {
                            Text(
                                text = "System Basic Info",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "Android version: $androidVersion",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = "Android Level: $sdkLevel",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = "Android Build ID: $androidID",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = "Brand: $brand",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = "Manufacturer: $manufacturer",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = "Model: $model",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = "Board: $board",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = "Build fingerprint: $fingerprint",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = "Product: $product",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(text = "SKU: $sku", style = MaterialTheme.typography.titleSmall)
                            Text(
                                text = "Build type : $buildType",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                    // 获取支持的解码器
                    fun getSupportedDecoderNames(): List<String> {
                        val codecList = MediaCodecList(MediaCodecList.ALL_CODECS).codecInfos
                        return codecList.filter { codecInfo -> !codecInfo.isEncoder } // Filtering out decoders only
                            .flatMap { codec ->
                                codec.supportedTypes.toList()
                            }.distinct()
                    }

                    // 获取支持的编码器
                    fun getSupportedEncoderNames(): List<String> {
                        val codecList = MediaCodecList(MediaCodecList.ALL_CODECS).codecInfos
                        return codecList.filter { codecInfo -> codecInfo.isEncoder } // Filtering out decoders only
                            .flatMap { codec ->
                                codec.supportedTypes.toList()
                            }.distinct()
                    }
                    OutlinedCard(
                        onClick = { /* 点击事件处理 */ },
                        modifier = Modifier
                            .padding(16.dp) // 外部间距
                            .fillMaxWidth() // 宽度填充父容器，但不是高度
                            .wrapContentHeight(), // 高度包裹内容
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.outlinedCardColors(MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(24.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "Media", style = MaterialTheme.typography.titleLarge)

                            Text(
                                text = "supported Encoder: ${getSupportedEncoderNames()}",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = "supported Decoder: ${getSupportedDecoderNames()}",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                }
            }

        }
    }
}


