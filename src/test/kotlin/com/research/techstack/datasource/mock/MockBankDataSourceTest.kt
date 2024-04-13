package com.research.techstack.datasource.mock

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class MockBankDataSourceTest {
    private val mockBankDataSource: MockBankDataSource = MockBankDataSource()

    @Test
    fun `should provide a collection of banks`(){
        // when
        val banks = mockBankDataSource.retrieveBanks()

        // then
        Assertions.assertThat(banks.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should return valid mock data`(){
        // when
        val banks = mockBankDataSource.retrieveBanks()

        // then
        Assertions.assertThat(banks).allMatch{bank -> bank.accountNumber.isNotBlank()}
        Assertions.assertThat(banks).anyMatch{bank -> bank.trust > 0.0}
        Assertions.assertThat(banks).anyMatch{bank -> bank.transactionFee > 0}
    }
}