    package com.example.strawberry_app.server

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.serverDataStore by preferencesDataStore("server_prefs")

@Singleton
class ServerRepository @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    companion object{
        private val KEY_IP = stringPreferencesKey("server_ip")
        private val KEY_PORT= intPreferencesKey("server_port")
        private val KEY_PASSWORD= stringPreferencesKey("server_password")
    }

    private val datastore = context.serverDataStore

    val serverInfoFlow: Flow<ServerInfo?> = datastore.data
        .catch { e ->
            if (e is IOException) emit(emptyPreferences()) else throw e
        }
        .map {
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

    suspend fun saveServerInfo(info: ServerInfo){
        datastore.edit {
            it[KEY_IP] = info.ip
            it[KEY_PORT] = info.port
            it[KEY_PASSWORD] = info.password
        }
    }
}