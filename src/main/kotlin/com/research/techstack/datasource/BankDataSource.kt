package com.research.techstack.datasource

import com.research.techstack.model.Bank

interface BankDataSource {
    fun retrieveBanks(): Collection<Bank>
    fun retrieveBank(id: String): Bank
    fun addBank(newBank: Bank): Bank
}