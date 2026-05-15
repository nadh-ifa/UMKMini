package com.example.ukmini.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ukmini.ui.auth.LoginScreen
import com.example.ukmini.ui.auth.RegisterScreen
import com.example.ukmini.ui.contact.AddContactScreen
import com.example.ukmini.ui.contact.ContactDetailScreen
import com.example.ukmini.ui.contact.ContactListScreen
import com.example.ukmini.ui.home.HomeScreen
import com.example.ukmini.viewmodel.AuthViewModel
import com.example.ukmini.viewmodel.ContactViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val contactViewModel: ContactViewModel = viewModel()

    val startDestination = if (authViewModel.isLoggedIn) "home" else "login"

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onGoToRegister = {
                    navController.navigate("register")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                authViewModel = authViewModel,
                onGoToLogin = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onRegisterSuccess = {
                    navController.navigate("home") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            HomeScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onOpenContacts = {
                    navController.navigate("contact_list")
                }
            )
        }

        composable("contact_list") {
            ContactListScreen(
                contactViewModel = contactViewModel,
                onAddContact = {
                    navController.navigate("add_contact")
                },
                onOpenDetail = { contactId ->
                    contactViewModel.getContactById(contactId)
                    navController.navigate("contact_detail")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("add_contact") {
            AddContactScreen(
                contactViewModel = contactViewModel,
                onSuccess = {
                    navController.navigate("contact_list") {
                        popUpTo("add_contact") { inclusive = true }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("contact_detail") {
            ContactDetailScreen(
                contact = contactViewModel.selectedContact,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}