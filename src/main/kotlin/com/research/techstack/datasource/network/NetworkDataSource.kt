package com.research.techstack.datasource.network

import com.research.techstack.datasource.BankDataSource
import com.research.techstack.datasource.network.dto.BankList
import com.research.techstack.model.Bank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.io.IOException

@Repository(value = "network")
class NetworkDataSource(@Autowired private val restTemplate: RestTemplate) : BankDataSource {
    override fun retrieveBanks(): Collection<Bank> {
        val response: ResponseEntity<BankList> = restTemplate.getForEntity("http://54.193.31.159/banks")
        return response.body?.result?: throw IOException("Could not fetch bank from network")
    }

    override fun retrieveBank(id: String): Bank {
        TODO("Not yet implemented")
    }

    override fun addBank(newBank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun updateBank(newBank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun deleteBank(id: String) {
        TODO("Not yet implemented")
    }
}