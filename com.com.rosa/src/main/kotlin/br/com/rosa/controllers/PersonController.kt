package br.com.rosa.controllers

import br.com.rosa.data.vo.v1.PersonVO
import br.com.rosa.data.vo.v2.PersonVO as PersonVOV2
import br.com.rosa.model.Person
import br.com.rosa.services.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/person")
class PersonController {

    @Autowired
    private lateinit var service: PersonService

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll() :  List<PersonVO>{
        return service.findAll()
    }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findById(@PathVariable("id") id: Long) : PersonVO{
        return service.findById(id)
    }

    @PostMapping(value = ["/v1"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody personVO: PersonVO) : PersonVO{
        return service.create(personVO);
    }

    @PostMapping(value = ["/v2"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createV2(@RequestBody personVO: PersonVOV2) : PersonVOV2{
        return service.createV2(personVO);
    }

    @PutMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun update(@RequestBody personVO: PersonVO) : PersonVO{
        return service.update(personVO)
    }

    @DeleteMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun delete(@PathVariable("id") id: Long): ResponseEntity<*>{
        service.delete(id)
        return ResponseEntity.noContent().build<Any>()
    }

}