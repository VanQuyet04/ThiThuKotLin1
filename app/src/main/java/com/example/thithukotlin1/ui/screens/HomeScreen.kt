package com.example.thithukotlin1.ui.screens

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.thithukotlin1.room.MayTinh
import com.example.thithukotlin1.room.MayTinhViewModelFactory
import com.example.thithukotlin1.room.MayTinhViewmodel
import java.text.DecimalFormat
import java.text.Normalizer
import java.util.regex.Pattern

var mayTinhTemp: MayTinh =
    MayTinh(ph35419_name = "", ph35419_price = 0f, ph35419_description = "", ph35419_status = false, ph35419_image = "")

@Composable
fun ItemText(content: String) {
    Text(
        text = content, Modifier.padding(4.dp), style = MaterialTheme.typography.labelLarge
    )
}

@Composable
fun HomeScreen() {
    //khai báo context và view model thao tác crud với UI
    val context = LocalContext.current
    val viewModel: MayTinhViewmodel =
        viewModel(factory = MayTinhViewModelFactory(context.applicationContext as Application))
    val mayTinhs by viewModel.getAll.observeAsState(emptyList())

    // Tìm kiếm
    var filteredMayTinhs by remember { mutableStateOf<List<MayTinh>>(emptyList()) }
    var searchKeyword by remember { mutableStateOf("") }

    var showDialogItemInfor by remember { mutableStateOf(false) } // xem chi tiết
    var sp: MayTinh? by remember { mutableStateOf(null) }

    // các state xác định mở hay đóg các dialog, mặc định là đóng
    var showDialogXoaSp by remember { mutableStateOf(false) }
    var showDialogThemMayTinh by remember { mutableStateOf(false) }
    var showDialogSuaMayTinh by remember { mutableStateOf(false) }

    if (showDialogItemInfor) {
        ShowDialoginfo(
            title = "Thông tin SP",
            mayTinh = sp ?: MayTinh(
                ph35419_name = "",
                ph35419_price = 0f,
                ph35419_description = "",
                ph35419_status = false,
                ph35419_image = ""
            ),
            onDismiss = { showDialogItemInfor = false },
            onConfirm = { showDialogItemInfor = false }
        )
    }

    if (showDialogXoaSp) {
        ShowDialog(
            title = "Xóa SP",
            content = "Bạn có chắc chắn xóa máy tính?",
            onDismiss = { showDialogXoaSp = false },
            onConfirm = {
                sp?.let { viewModel.delete(it) }
                showDialogXoaSp = false
            }
        )
    }

    if (showDialogThemMayTinh) {
        ShowDialogThemSuaSP(
            title = "Thêm SP",
            onDismiss = { showDialogThemMayTinh = false },
            onConfirm = {
                viewModel.insert(mayTinhTemp.copy())
                showDialogThemMayTinh = false
                mayTinhTemp =
                    MayTinh(ph35419_name = "", ph35419_price = 0f, ph35419_description = "", ph35419_status = false, ph35419_image = "")
            }
        )
    }

    if (showDialogSuaMayTinh) {
        ShowDialogThemSuaSP(
            title = "Sửa SP",
            mayTinh = sp,
            onDismiss = { showDialogSuaMayTinh = false },
            onConfirm = {
                viewModel.update(mayTinhTemp.copy())
                showDialogSuaMayTinh = false
                mayTinhTemp =
                    MayTinh(ph35419_name = "", ph35419_price = 0f, ph35419_description = "", ph35419_status = false, ph35419_image = "")
            }
        )
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Quản lý máy tính",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        OutlinedTextField(
            value = searchKeyword,
            onValueChange = { searchKeyword = it },
            label = { Text("Tìm kiếm máy tính") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Box(
            Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(if (searchKeyword.isNotEmpty()) filteredMayTinhs else mayTinhs) { item ->
                    MayTinhItem(
                        mayTinh = item,
                        onItemClicked = {
                            sp = it
                            showDialogItemInfor = true
                        },
                        onEditClicked = {
                            sp = it.copy()
                            mayTinhTemp = it.copy()
                            showDialogSuaMayTinh = true
                        },
                        onDeleteClicked = {
                            sp = it
                            showDialogXoaSp = true
                        }
                    )
                }
            }

            FloatingActionButton(
                onClick = { showDialogThemMayTinh = true },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }

            DisposableEffect(searchKeyword) {
                if (searchKeyword.isNotEmpty()) {
                    val keyword = removeAccents(searchKeyword).lowercase()
                    filteredMayTinhs = mayTinhs.filter {
                        removeAccents(it.ph35419_name).lowercase().contains(keyword)
                    }
                } else {
                    filteredMayTinhs = emptyList()
                }
                onDispose { }
            }


        }
    }

}


@Composable
fun ShowDialogThemSuaSP(
    title: String,
    mayTinh: MayTinh? = null,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var ph35419_name by remember { mutableStateOf(mayTinh?.ph35419_name ?: "") }
    var ph35419_price by remember { mutableStateOf(mayTinh?.ph35419_price?.toString() ?: "") }
    var ph35419_description by remember { mutableStateOf(mayTinh?.ph35419_description ?: "") }
    var ph35419_status by remember { mutableStateOf(mayTinh?.ph35419_status ?: false) }
    var ph35419_image by remember { mutableStateOf(mayTinh?.ph35419_image ?: "") }

    // Text error state
    var ph35419_nameError by remember { mutableStateOf(false) }
    var ph35419_priceError by remember { mutableStateOf(false) }
    var ph35419_descriptionError by remember { mutableStateOf(false) }
    var ph35419_imageError by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            uri?.let {
                ph35419_image = it.toString()
                try {
                    val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    context.contentResolver.takePersistableUriPermission(it, flag)
                } catch (e: SecurityException) {
                    Toast.makeText(
                        context,
                        "Không thể cấp quyền truy cập cho tệp tin được chọn",
                        Toast.LENGTH_SHORT
                    ).show()
                    e.printStackTrace()
                }
            }
        }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        containerColor = Color.White,
        confirmButton = {
            Button(
                onClick = {
                    ph35419_nameError = ph35419_name.isEmpty()
                    ph35419_priceError = ph35419_price.isEmpty()
                    ph35419_descriptionError = ph35419_description.isEmpty()
                    ph35419_imageError = ph35419_image.isEmpty()

                    if (!ph35419_nameError && !ph35419_priceError && !ph35419_descriptionError && !ph35419_imageError) {
                        val updatedMayTinh = mayTinh?.copy(
                            ph35419_name = ph35419_name,
                            ph35419_price = ph35419_price.toBigDecimal().toFloat(),
                            ph35419_description = ph35419_description,
                            ph35419_status = ph35419_status,
                            ph35419_image = ph35419_image
                        ) ?: MayTinh(
                            ph35419_name = ph35419_name,
                            ph35419_price = ph35419_price.toBigDecimal().toFloat(),
                            ph35419_description = ph35419_description,
                            ph35419_status = ph35419_status,
                            ph35419_image = ph35419_image
                        )
                        mayTinhTemp = updatedMayTinh
                        onConfirm()
                    }
                },
                colors = ButtonDefaults.buttonColors(Color.Blue)
            ) {
                Text(text = "Xác nhận")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Text(text = "Hủy", color = Color.Red)
            }
        },
        title = {
            Text(text = title)
        },
        text = {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Gray)
                        .clickable {
                            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (ph35419_image.isNotEmpty()) {
                        AsyncImage(
                            model = ph35419_image,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text(text = "Chọn ảnh", color = Color.White)
                    }
                }

                if (ph35419_imageError) {
                    Text(
                        text = "Ảnh máy tính không được để trống",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                TextField(
                    value = ph35419_name,
                    onValueChange = {
                        ph35419_name = it
                        ph35419_nameError = it.isEmpty()
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    label = { Text("Tên máy tính") },
                    isError = ph35419_nameError
                )
                if (ph35419_nameError) {
                    Text(
                        text = "Tên máy tính không được để trống",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                TextField(
                    value = ph35419_price,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    onValueChange = {
                        ph35419_price = it
                        ph35419_priceError = it.isEmpty()
                    },
                    label = { Text("Giá máy tính") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    isError = ph35419_priceError
                )
                if (ph35419_priceError) {
                    Text(
                        text = "Giá máy tính không được để trống",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                TextField(
                    value = ph35419_description,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ), onValueChange = {
                        ph35419_description = it
                        ph35419_descriptionError = it.isEmpty()
                    },
                    label = { Text("Mô tả máy tính") },
                    isError = ph35419_descriptionError
                )
                if (ph35419_descriptionError) {
                    Text(
                        text = "Mô tả máy tính không được để trống",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = ph35419_status,
                        onCheckedChange = { ph35419_status = it }
                    )
                    Text(text = "Trạng thái máy tính")
                }
            }
        }
    )
}

@Composable
fun ShowDialog(
    title: String, content: String, onDismiss: () -> Unit, onConfirm: () -> Unit
) {
    AlertDialog(onDismissRequest = { onDismiss() }, containerColor = Color.White, confirmButton = {
        Button(onClick = { onConfirm() },colors = ButtonDefaults.buttonColors(Color.Red)) {
            Text(text = "Xác nhận")
        }
    }, dismissButton = {
        Button(onClick = { onDismiss() }, colors = ButtonDefaults.buttonColors(Color.White)) {
            Text(text = "Hủy", color = Color.Black)
        }
    }, title = { Text(text = title) },
        text = { Text(text = content) })
}

@Composable
fun ShowDialoginfo(
    title: String, mayTinh: MayTinh, onDismiss: () -> Unit, onConfirm: () -> Unit
) {
    AlertDialog(onDismissRequest = { onDismiss() }, containerColor = Color.White, confirmButton = {
        Button(onClick = { onConfirm() },colors = ButtonDefaults.buttonColors(Color.Blue)) {
            Text(text = "Close")
        }
    }, title = { Text(text = title) },
        text = {
            Column {
                AsyncImage(
                    model = mayTinh.ph35419_image,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 10.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
                Text(text = "Tên máy tính: ${mayTinh.ph35419_name}")
                Text(text = "Giá máy tính: ${mayTinh.ph35419_price}")
                Text(text = "Mô tả máy tính: ${mayTinh.ph35419_description ?: ""}")
                Text(text = "Trạng thái máy tính: ${if (mayTinh.ph35419_status) "Mới" else "Cũ"}")
            }
        })
}

@Composable
fun MayTinhItem(
    mayTinh: MayTinh,
    onItemClicked: (MayTinh) -> Unit,
    onEditClicked: (MayTinh) -> Unit,
    onDeleteClicked: (MayTinh) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onItemClicked(mayTinh) },
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Row(
                Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = mayTinh.ph35419_image,
                    contentDescription = "",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    ItemText(content = "Tên: ${mayTinh.ph35419_name}")
                    ItemText(content = "Giá: ${mayTinh.ph35419_price}")
                    ItemText(content = "Mô tả: ${mayTinh.ph35419_description}")
                    ItemText(content = "Trạng thái: ${if (mayTinh.ph35419_status) "Mới" else "Cũ"}")
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { onEditClicked(mayTinh) }) {
                    Text(text = "Update", color = Color.Blue)
                }
                TextButton(onClick = { onDeleteClicked(mayTinh) }) {
                    Text(text = "Delete", color = Color.Red)
                }
            }
        }
    }
}


// Hàm chuyển đổi chuỗi có dấu thành không dấu
fun removeAccents(input: String): String {
    val normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
    val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    return pattern.matcher(normalized).replaceAll("")
}
