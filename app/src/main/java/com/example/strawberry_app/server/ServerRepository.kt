package com.example.strawberry_app.server

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.serverDataStore by preferencesDataStore("server_prefs")

class ServerRepository @Inject constructor(
    @ApplicationContext private val context: Context
)
{
    private val datastore = context.serverDataStore

    val serverInfoFlow: Flow<ServerInfo?> = datastore.data.map {
        val ip = it[KEY_IP]
        if(ip.isNullOrBlank()){
            null
        }else{
            ServerInfo(
                ip = ip,
                port = it[KEY_PORT] ?: 5000,
                password = it[KEY_PASSWORD] ?: ""
            )
        }
    }.distinctUntilChanged()

    companion object{
        val KEY_IP = stringPreferencesKey("server_ip")
        val KEY_PORT= intPreferencesKey("server_port")
        val KEY_PASSWORD= stringPreferencesKey("server_password")
    }

    suspend fun saveServerInfo(info: ServerInfo){
        datastore.edit {
            it[KEY_IP] = info.ip
            it[KEY_PORT] = info.port
            it[KEY_PASSWORD] = info.password
        }
    }
}