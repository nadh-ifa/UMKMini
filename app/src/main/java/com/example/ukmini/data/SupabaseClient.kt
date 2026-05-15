package com.example.ukmini.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://ercdlfigcpjfgwxvenoy.supabase.co",
        supabaseKey = "sb_publishable_tOdVKijFRKC5l-ntzoMahw_MSKZdp1J"
    ) {
        install(Auth)
        install(Postgrest)
    }
}