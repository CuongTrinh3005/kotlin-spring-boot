package com.research.techstack.service

import com.research.techstack.datasource.BankDataSource
import com.research.techstack.model.Bank
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class BankServiceTest {
    private val dataSource : BankDataSource = mockk<BankDataSource>(relaxed = true)
    private val bankService:BankService = BankService(dataSource)

    @Test
    fun `should use bank data source to retrieve banks`() {
        // when
        bankService.getBanks()

        // then
        // verify datasource is called to retrieve data only 1 time
        // because we do not assert the return type here so can use mockk(relaxed=true)
        verify (exactly = 1) { dataSource.retrieveBanks() }
    }

    @Test
    fun `should return specific bank given a valid account number`() {
        // given
        val accountNumber = "abc"
        val existedBank = Bank(accountNumber, 0.0, 0)
        every { dataSource.retrieveBank(any()) } returns existedBank

        // when
        val bank: Bank = bankService.getBank(accountNumber)

        // then
        Assertions.assertThat(bank.accountNumber).isEqualTo(accountNumber)
    }
}