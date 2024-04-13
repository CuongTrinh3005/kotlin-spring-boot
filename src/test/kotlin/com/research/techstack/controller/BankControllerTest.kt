package com.research.techstack.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.research.techstack.model.Bank
import io.mockk.InternalPlatformDsl.toStr
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.nio.charset.StandardCharsets

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    val baseUrl = "/api/banks"

    @Test
    fun `should return all banks`() {
        mockMvc.get(baseUrl)
            .andDo { println() }
            .andExpect {
                status {
                    isOk()
                }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.size()") { value(3) }
                jsonPath("$[0].accountNumber") { isString() }
                jsonPath("$[0].trust") { isNumber() }
                jsonPath("$[0].transactionFee") { isNumber() }
            }
    }

    @Test
    fun `should return a specific bank given a valid account number`() {
        // given
        val accountNumber = "abc"

        // when/ then
        mockMvc.get("$baseUrl/$accountNumber")
            .andDo { println() }
            .andExpect {
                status {
                    isOk()
                }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
                jsonPath("$.accountNumber") {value(accountNumber)}
            }
    }

    @Test
    fun `should return NOT FOUND for non-exist account number`() {
        // given
        val accountNumber = "does-not-existed"

        // when/ then
        mockMvc.get("$baseUrl/$accountNumber")
            .andDo { println() }
            .andExpect {
                status {
                    isNotFound()
                }
                content {
                    contentType(MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8))
                        .toStr()
                        .contentEquals("Bank with account number $accountNumber not found")
                }
            }
    }

    @Test
    fun `should create a bank`() {
        // given
        val newBank: Bank = Bank("new-bank", 10.20, 3)

        // when
        val result = mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(newBank)
        }

        // then
        result.andDo { println() }
            .andExpect {
                status {
                    isCreated()
                }
                jsonPath("$.accountNumber") {value(newBank.accountNumber)}
                jsonPath("$.trust") {value(newBank.trust)}
                jsonPath("$.transactionFee") {value(newBank.transactionFee)}
            }
    }

    @Test
    fun `should return BAD REQUEST if add an bank account number exists`() {
        // given
        val existBank = Bank("abc", 0.0, 0)

        // when
        val result = mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(existBank)
        }

        // then
        result.andDo { println() }
            .andExpect {
                status {
                    isBadRequest()
                }
                content {
                    contentType(MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8))
                        .toStr()
                        .contentEquals("Bank with account number ${existBank.accountNumber} does exist")
                }
            }
    }
}