package com.example.ukmini.ui.contact

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ukmini.viewmodel.ContactViewModel

@Composable
fun ContactListScreen(
    contactViewModel: ContactViewModel,
    onAddContact: () -> Unit,
    onOpenDetail: (String) -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        contactViewModel.getContacts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text("Kontak Penjual", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAddContact,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tambah Kontak Penjual")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kembali")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (contactViewModel.isLoading) {
            Text("Loading...")
        }

        if (contactViewModel.message.isNotBlank()) {
            Text(contactViewModel.message)
            Spacer(modifier = Modifier.height(8.dp))
        }

        LazyColumn {
            items(contactViewModel.contacts) { contact ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable {
                            contact.id?.let { onOpenDetail(it) }
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = contact.store_name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text("WhatsApp: ${contact.whatsapp ?: "-"}")
                        Text("Alamat: ${contact.address ?: "-"}")
                    }
                }
            }
        }
    }
}