package com.research.techstack.service

import com.research.techstack.datasource.BankDataSource
import com.research.techstack.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val dataSource: BankDataSource) {
    fun getBanks(): Collection<Bank> {
        return dataSource.retrieveBanks()
    }

    fun getBank(id: String): Bank {
        return dataSource.retrieveBank(id)
    }

    fun addBank(newBank: Bank): Bank {
        return dataSource.addBank(newBank)
    }

    fun updateBank(newBank: Bank): Bank {
        return dataSource.updateBank(newBank)
    }

    fun deleteBank(id: String) {
        dataSource.deleteBank(id)
    }
}