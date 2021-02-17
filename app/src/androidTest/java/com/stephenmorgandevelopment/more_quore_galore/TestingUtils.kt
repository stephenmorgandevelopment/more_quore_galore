package com.stephenmorgandevelopment.more_quore_galore

import com.stephenmorgandevelopment.more_quore_galore.models.Client
import com.stephenmorgandevelopment.more_quore_galore.models.Location
import com.stephenmorgandevelopment.more_quore_galore.models.ProfilePicture

class TestingUtils {

    companion object {
        fun generateMockData() : List<Client> {
            val mockedClients = ArrayList<Client>(5)
            for (i in 0 until 5 step 1) {
                mockedClients.add(
                    Client(
                        (100L + i),
                        i.toLong(),
                        java.lang.String.format("Mock %d", i),
                        java.lang.String.format("(%d0%d)%d0%d-0000", i, i, i, i),
                        ProfilePicture.getEmpty(),
                        Location.getEmpty(),
                        java.lang.String.format("%d reasons", i),
                        listOf(String.format("10%d problems", i))
                    )
                )
            }

            return mockedClients
        }
    }

}

//        val mockClientOne = Client(
//            101,
//            1,
//            "Mock One",
//            "(101)101-0000",
//            ProfilePicture.getEmpty(),
//            Location.getEmpty(),
//            "101 reasons",
//            listOf("10%d problems")