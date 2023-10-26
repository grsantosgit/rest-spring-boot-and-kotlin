package br.com.rosa.controllers

import br.com.rosa.data.vo.v1.PersonVO
import br.com.rosa.services.PersonService
import br.com.rosa.util.MediaType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import br.com.rosa.data.vo.v2.PersonVO as PersonVOV2

@RestController
@RequestMapping("/person")
class PersonController {

    @Autowired
    private lateinit var service: PersonService


    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun findAll() :  List<PersonVO>{
        return service.findAll()
    }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun findById(@PathVariable("id") id: Long) : PersonVO{
        return service.findById(id)
    }

    @PostMapping(value = ["/v1"], consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun create(@RequestBody personVO: PersonVO) : PersonVO{
        return service.create(personVO);
    }

    @PostMapping(value = ["/v2"], consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun createV2(@RequestBody personVO: PersonVOV2) : PersonVOV2{
        return service.createV2(personVO);
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun update(@RequestBody personVO: PersonVO) : PersonVO{
        return service.update(personVO)
    }

    @DeleteMapping("/{id}", consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun delete(@PathVariable("id") id: Long): ResponseEntity<*>{
        service.delete(id)
        return ResponseEntity.noContent().build<Any>()
    }

}