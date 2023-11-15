package com.example.myapplication

import android.content.Context
import android.media.MediaCodecList
import android.os.Build
import android.os.Bundle
import android.view.Display
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

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
                    val showCardDialog = remember { mutableStateOf(false) } // 关于页面
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
                            IconButton(
                                onClick = { showCardDialog.value = true },
                                colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.surface),
                                modifier = Modifier.clip(RoundedCornerShape(64.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.TwoTone.Favorite,
                                    contentDescription = null,
                                )
                            }
                        }
                    )

                    if (showCardDialog.value) {
                        AlertDialog(
                            onDismissRequest = { showCardDialog.value = false },
                            title = { Text("About", style = MaterialTheme.typography.titleMedium) },
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically, // 行容器中内容垂直居中
                                    horizontalArrangement = Arrangement.Center // 行容器中内容水平居中
                                ) {
                                    Spacer(modifier = Modifier.padding(5.dp))
                                    Image(
                                        painter = painterResource(id = R.drawable.unnamed),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(CircleShape)

                                    )
                                    Spacer(modifier = Modifier.padding(5.dp))
                                    Text(
                                        text = "Maintainer: @lurenjia534",
                                        style = MaterialTheme.typography.bodySmall,

                                        )
                                }
                            },
                            containerColor = MaterialTheme.colorScheme.surface,
                            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            textContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            confirmButton = {
                                TextButton(onClick = { showCardDialog.value = false }) {
                                    Text("Ok", style = MaterialTheme.typography.bodyMedium)
                                }

                            })
                    }

                    fun getCpuInfo(): String {
                        try {
                            val process = Runtime.getRuntime().exec("cat /proc/cpuinfo")
                            val inputStream = process.inputStream
                            val reader = BufferedReader(InputStreamReader(inputStream))

                            val stringBuilder = StringBuilder()
                            var line: String?

                            while (reader.readLine().also { line = it } != null) {
                                stringBuilder.append(line).append("\n")
                            }

                            inputStream.close()

                            return stringBuilder.toString()
                        } catch (e: IOException) {
                            e.printStackTrace()
                            return "Unable to read CPU info:${e.printStackTrace()}"
                        }
                    }

                    val hardware = Build.SOC_MODEL

                    val cpuInfo = remember { mutableStateOf(false) }

                    fun copyText(text: String, clipboardManager: ClipboardManager) {
                        clipboardManager.setText(AnnotatedString(text))
                    }

                    OutlinedCard(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.outlinedCardColors(MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(24.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.Start, // 水平对其方式
                            verticalArrangement = Arrangement.spacedBy(8.dp) // 文本之间的间隔
                        ) {
                            Text(text = "Hardware", style = MaterialTheme.typography.titleLarge)
                            Column {
                                Text(
                                    text = "Soc model",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(text = hardware)
                            }
                            Column {
                                Text(
                                    text = "Click cpu Info",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                TextButton(onClick = { cpuInfo.value = true }) {
                                    Text(text = "CPU Info")
                                }
                            }
                        }
                    }

                    if (cpuInfo.value) {
                        val clipboardManager = LocalClipboardManager.current
                        AlertDialog(
                            onDismissRequest = { cpuInfo.value = false },
                            title = {
                                Text(
                                    text = "Cpu Info",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                            text = {
                                Row(modifier = Modifier.verticalScroll(rememberScrollState())) {
                                    Spacer(modifier = Modifier.padding(5.dp))
                                    Text(text = getCpuInfo())
                                }
                            },
                            confirmButton = {
                                Row {
                                    TextButton(onClick = {
                                        copyText(getCpuInfo(), clipboardManager)
                                    }) {
                                        Text("Copy", style = MaterialTheme.typography.bodyMedium)
                                    }
                                    TextButton(onClick = { cpuInfo.value = false }) {
                                        Text("Ok", style = MaterialTheme.typography.bodyMedium)
                                    }
                                }
                            },
                        )
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
                    // val sku = Build.SKU // SKU 这里可能指设备唯一代号 比如Redmi Note 12 Turbo 代号: Marble

                    fun copySystemBasicInfoCard(
                        androidString: String,
                        sdkLevel: String,
                        androidID: String,
                        brand: String,
                        manufacturer: String,
                        model: String,
                        board: String,
                        fingerprint: String,
                        product: String,
                        buildType: String,
                    ): String {
                        return "true"
                    }

                    OutlinedCard(
                        onClick = { },
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
                            // Text(text = "SKU: $sku", style = MaterialTheme.typography.titleSmall)
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

                    // if HDR10
                    fun getHDR(context: Context): Boolean {
                        if (Build.VERSION.SDK_INT >= 34) {
                            val display = context.display
                            display?.let {
                                val mode = it.mode
                                return mode.supportedHdrTypes.contains(Display.HdrCapabilities.HDR_TYPE_HDR10)
                            }
                            return false
                        } else {
                            val display = context.display
                            display?.let {
                                val hdrCapabilities = it.hdrCapabilities
                                hdrCapabilities?.let { capabilities ->
                                    return capabilities.supportedHdrTypes.contains(Display.HdrCapabilities.HDR_TYPE_HDR10)
                                }
                            }
                            return false
                        }
                        return false
                    }

                    // if HDR10 Plus
                    fun getHdrPlus(context: Context): Boolean {
                        if (Build.VERSION.SDK_INT >= 34) {
                            val display = context.display
                            display?.let {
                                val mode = it.mode
                                return mode.supportedHdrTypes.contains(Display.HdrCapabilities.HDR_TYPE_HDR10_PLUS)
                            }
                            return false
                        } else {
                            val display = context.display
                            display?.let {
                                val hdrCapabilities = it.hdrCapabilities
                                hdrCapabilities?.let { capabilities ->
                                    return capabilities.supportedHdrTypes.contains(Display.HdrCapabilities.HDR_TYPE_HDR10_PLUS)
                                }
                            }
                            return false
                        }
                    }

                    // if HLG
                    fun getHLG(context: Context): Boolean {
                        if (Build.VERSION.SDK_INT >= 34) {
                            val display = context.display
                            display?.let {
                                val mode = it.mode
                                return mode.supportedHdrTypes.contains(Display.HdrCapabilities.HDR_TYPE_HLG)
                            }
                        } else {
                            val display = context.display
                            display?.let {
                                val hdrCapabilities = it.hdrCapabilities
                                hdrCapabilities?.let { capabilities ->
                                    return capabilities.supportedHdrTypes.contains(Display.HdrCapabilities.HDR_TYPE_HLG)
                                }
                            }
                        }
                        return false
                    }

                    // if Dolby Vision
                    fun getDolbyVision(context: Context): Boolean {
                        if (Build.VERSION.SDK_INT <= 34) {
                            val display = context.display
                            display?.let {
                                val mode = it.mode
                                return mode.supportedHdrTypes.contains(Display.HdrCapabilities.HDR_TYPE_DOLBY_VISION)
                            }
                        } else {
                            val display = context.display
                            display?.let {
                                val hdrCapabilities = it.hdrCapabilities
                                hdrCapabilities?.let { capabilities ->
                                    return capabilities.supportedHdrTypes.contains(Display.HdrCapabilities.HDR_TYPE_DOLBY_VISION)
                                }
                            }
                        }
                        return false
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

                            Column {
                                Text(
                                    text = "supported Encoder",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "${getSupportedEncoderNames()}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Column {
                                Text(
                                    text = "supported Decoder",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "${getSupportedDecoderNames()}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Column {
                                Text(text = "HDR 10", style = MaterialTheme.typography.titleMedium)
                                Text(
                                    text = "${getHDR(LocalContext.current)}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Column {
                                Text(
                                    text = "HDR 10+ ",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "${getHdrPlus(LocalContext.current)}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Column {
                                Text(text = "HLG", style = MaterialTheme.typography.titleMedium)
                                Text(
                                    text = "${getHLG(LocalContext.current)}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Column {
                                Text(
                                    text = "DOLBY VISION",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "${getDolbyVision(LocalContext.current)}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


