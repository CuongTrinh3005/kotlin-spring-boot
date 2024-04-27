package com.research.techstack.datasource.mock

import com.research.techstack.datasource.BankDataSource
import com.research.techstack.model.Bank
import org.springframework.stereotype.Repository

@Repository(value = "mock")
class MockBankDataSource : BankDataSource {
    val banks = mutableListOf(
        Bank("abc", 1.0, 1),
        Bank("102", 30.0, 10),
        Bank("405", .0, 50)
    )

    override fun retrieveBanks(): Collection<Bank> = this.banks

    override fun retrieveBank(id: String): Bank {
        return banks.singleOrNull() { it.accountNumber == id }
            ?: throw NoSuchElementException("Bank with account number $id not found")
    }

    override fun addBank(newBank: Bank): Bank {
        val doesExist = banks.any{it.accountNumber == newBank.accountNumber}
        if(doesExist)
            throw IllegalArgumentException("Bank with account number ${newBank.accountNumber} does exist")

        banks.add(newBank)
        return newBank
    }

    override fun updateBank(newBank: Bank): Bank {
        val doesExist = banks.any{it.accountNumber == newBank.accountNumber}
        if(!doesExist)
            throw NoSuchElementException("Bank with account number ${newBank.accountNumber} not found")

        val existBank = banks.single{it.accountNumber == newBank.accountNumber}

        existBank.accountNumber = newBank.accountNumber
        existBank.trust = newBank.trust
        existBank.transactionFee = newBank.transactionFee

        return existBank
    }

    override fun deleteBank(id: String) {
        val doesExist = banks.any{it.accountNumber == id}
        if(!doesExist)
            throw NoSuchElementException("Bank with account number $id not found")

        banks.removeIf{it.accountNumber == id}
    }
}