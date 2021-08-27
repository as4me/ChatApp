package com.apusx.chatapp

import org.junit.Assert.*


import junit.framework.TestCase

import org.junit.Test

class SignUPTest : TestCase() {
    private val validator = SignUP()

    @Test
    fun validateemail() {
        val email = "test@test.test"

        assertTrue(validator.validateemail(email))
    }
}