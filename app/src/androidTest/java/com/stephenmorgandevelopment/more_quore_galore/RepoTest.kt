package com.stephenmorgandevelopment.more_quore_galore

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stephenmorgandevelopment.more_quore_galore.data.ClientDatabase
import com.stephenmorgandevelopment.more_quore_galore.doas.ClientDao
import com.stephenmorgandevelopment.more_quore_galore.models.SimpleClient
import com.stephenmorgandevelopment.more_quore_galore.repos.ArticleRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith


//@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
//@Config(application = HiltTestApplication::class)
class RepoTest {
    private val application: Context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var articleRepo: ArticleRepo
    private lateinit var clientDatabase: ClientDatabase
    private lateinit var clientDao: ClientDao

//    @Mock private lateinit var service: ClientService

//    @Module
//    @InstallIn(SingletonComponent::class)
//    object ServiceModule {
//        @Provides
//        fun provideClientService() =
//    }

//    @get:Rule val hilt = HiltAndroidRule(this)

    @Before
    fun init() {
        initRepo()
        insertMockData()
    }

    @After
    fun tearDown() {
        clientDatabase.close()
    }

    @Test
    fun getClient_returnsClientData() {
        val client = articleRepo.getClient(101L)

        assertEquals(client.name, "Mock 1")   //.equals("Mock 1")
        assertEquals(client.serviceReason, "1 reasons")
    }

    @Test
    fun verifyDatabaseEntriesMatchMockedDataSize() {
        var simpleClients: LiveData<List<SimpleClient>>

        runBlocking(Dispatchers.Main) {
            simpleClients = articleRepo.getAll()

            simpleClients.observeForever(observer)

            Thread.sleep(3000)

            simpleClients.removeObserver(observer)
        }
    }

    val observer = Observer<List<SimpleClient>>() {
        assertEquals(it.size, 5)
    }


    private fun initRepo() {
        val conMan =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        clientDatabase =
            Room.inMemoryDatabaseBuilder(application, ClientDatabase::class.java)
                .build()

        clientDao = clientDatabase.clientDao()

//              DataModule.provideClientDao(DataModule.provideClientDatabase(application))

        articleRepo = ArticleRepo(conMan, null, clientDao)
    }

    private fun insertMockData() {
        val mockedClients = TestingUtils.generateMockData()

        runBlocking {
            clientDao.insertAll(mockedClients)
        }
    }
}