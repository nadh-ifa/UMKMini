package com.example.ukmini.ui.contact

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ukmini.model.SellerContact

@Composable
fun ContactDetailScreen(
    contact: SellerContact?,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Detail Kontak Penjual", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        if (contact == null) {
            Text("Kontak tidak ditemukan")

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = onBack) {
                Text("Kembali")
            }
        } else {
            Text("Nama Toko: ${contact.store_name}")
            Spacer(modifier = Modifier.height(8.dp))

            Text("WhatsApp: ${contact.whatsapp ?: "-"}")
            Spacer(modifier = Modifier.height(8.dp))

            Text("Alamat: ${contact.address ?: "-"}")
            Spacer(modifier = Modifier.height(8.dp))

            Text("Instagram: ${contact.instagram ?: "-"}")
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val phone = contact.whatsapp.orEmpty()
                        .replace("+", "")
                        .replace(" ", "")
                        .replace("-", "")

                    if (phone.isNotBlank()) {
                        val url = "https://wa.me/$phone"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Hubungi via WhatsApp")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Kembali")
            }
        }
    }
}