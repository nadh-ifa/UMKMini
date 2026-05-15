package com.example.ukmini.ui.contact

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ukmini.viewmodel.ContactViewModel

@Composable
fun AddContactScreen(
    contactViewModel: ContactViewModel,
    onSuccess: () -> Unit,
    onBack: () -> Unit
) {
    var storeName by remember { mutableStateOf("") }
    var whatsapp by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var instagram by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Tambah Kontak Penjual", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = storeName,
            onValueChange = { storeName = it },
            label = { Text("Nama Toko / UMKM") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = whatsapp,
            onValueChange = { whatsapp = it },
            label = { Text("Nomor WhatsApp, contoh: 628123456789") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = instagram,
            onValueChange = { instagram = it },
            label = { Text("Instagram, contoh: @tokoumkm") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                contactViewModel.addContact(
                    storeName = storeName,
                    whatsapp = whatsapp,
                    address = address,
                    instagram = instagram,
                    onSuccess = onSuccess
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (contactViewModel.isLoading) "Menyimpan..." else "Simpan Kontak")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Batal")
        }

        if (contactViewModel.message.isNotBlank()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(contactViewModel.message)
        }
    }
}