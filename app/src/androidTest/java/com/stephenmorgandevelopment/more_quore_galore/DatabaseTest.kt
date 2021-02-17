package com.stephenmorgandevelopment.more_quore_galore

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stephenmorgandevelopment.more_quore_galore.data.ClientDatabase
import com.stephenmorgandevelopment.more_quore_galore.doas.ClientDao
import com.stephenmorgandevelopment.more_quore_galore.models.Client
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var clientDao: ClientDao
    private lateinit var clientDatabase: ClientDatabase

    @Before
    fun init() {
        createDatabase()
        populateDatabaseWithMockData()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        clientDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadEmptyClient() {
        val client: Client = Client.getEmpty()

        runBlocking {
            clientDao.insert(client)
        }

        val phoneNumber = "(000)000-0000"

        assertEquals(clientDao.load(0).phoneNumber, phoneNumber)
    }

    @Test
    @Throws(Exception::class)
    fun verifyClientListSize() {
        val simpleClients = clientDao.loadSimpleClients()

        assertEquals(simpleClients.size, 5)
    }

    @Test
    @Throws(Exception::class)
    fun checkDataFromList() {
        runBlocking {
            val client103 = clientDao.load(103)

            assertEquals(client103.name, "Mock 3")
            assertEquals(client103.visitOrder, 3)
        }
    }



    private fun createDatabase() {
        val context: Context = ApplicationProvider.getApplicationContext()
        clientDatabase = Room.inMemoryDatabaseBuilder(
            context,
            ClientDatabase::class.java).build()
        clientDao = clientDatabase.clientDao()
    }

    private fun populateDatabaseWithMockData() {
        val mockedClients = TestingUtils.generateMockData()

        runBlocking {
            clientDao.insertAll(mockedClients)
        }
    }
}