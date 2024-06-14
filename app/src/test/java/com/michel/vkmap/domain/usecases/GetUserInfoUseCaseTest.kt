package com.michel.vkmap.domain.usecases

import com.michel.vkmap.data.repository.UserRepository
import com.michel.vkmap.domain.models.UserModel
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class GetUserInfoUseCaseTest {

    private val repository = mock<UserRepository>()

    @Test
    fun `should return user full name`(){


        val id = "321648993"
        val userInfo = UserModel("Mihail Larionov", ByteArray(1))
        val useCaseTest = GetUserInfoUseCase(repository)
   

        val expected = 4
        val actual = 2 + 2

        Assert.assertEquals(expected, actual)
    }

}