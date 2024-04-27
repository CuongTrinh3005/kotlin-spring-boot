package com.research.techstack.controller

import com.research.techstack.model.Bank
import com.research.techstack.service.BankService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("api/banks")
class BankController(private val bankService: BankService) {
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoItemFound(error: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(error.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(error: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(error.message, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(IOException::class)
    fun handleIOException(error: IOException): ResponseEntity<String> =
        ResponseEntity(error.message, HttpStatus.INTERNAL_SERVER_ERROR)

    @GetMapping
    fun getBanks(): Collection<Bank> = bankService.getBanks()

    @GetMapping("{id}")
    fun getBank(@PathVariable id: String): Bank {
        return bankService.getBank(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBank(@RequestBody newBank: Bank): Bank {
        return bankService.addBank(newBank)
    }

    @PatchMapping
    fun updateBank(@RequestBody newBank: Bank) = bankService.updateBank(newBank)

    @DeleteMapping("{id}")
    fun deleteBank(@PathVariable id: String) {
        bankService.deleteBank(id)
    }
}