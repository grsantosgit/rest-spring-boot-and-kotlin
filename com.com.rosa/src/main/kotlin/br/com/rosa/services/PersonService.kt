package br.com.rosa.services

import br.com.rosa.controllers.PersonController
import br.com.rosa.data.vo.v1.PersonVO
import br.com.rosa.data.vo.v2.PersonVO as PersonVOV2
import br.com.rosa.exceptions.ResourceNotFoundException
import br.com.rosa.mapper.DozerMapper
import br.com.rosa.mapper.custom.PersonMapper
import br.com.rosa.model.Person
import br.com.rosa.repositories.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var repository: PersonRepository

    @Autowired
    private lateinit var mapper: PersonMapper

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findById(id: Long) : PersonVO{
        logger.info("Finding one Person with ${id}!")

        val person = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }

        val personVO = DozerMapper.parseObject(person, PersonVO::class.java)
        val withSelRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelRel)
        return personVO
    }

    fun findAll(): List<PersonVO> {
        logger.info("Finding all People!")
        val persons = repository.findAll()

        val personsVO = DozerMapper.parseListObjects(persons, PersonVO::class.java)

        for(person in personsVO){
            val withSelRel = linkTo(PersonController::class.java).slash(person.key).withSelfRel()
            person.add(withSelRel)
        }

        return personsVO
    }

    fun create(personVO: PersonVO): PersonVO {
        logger.info("Creating one Person with name: ${personVO.firstName}")

        val person = DozerMapper.parseObject(personVO, Person::class.java)
        repository.save(person)

        val personVO = DozerMapper.parseObject(person, PersonVO::class.java)
        val withSelRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelRel)
        return personVO

    }

    fun update(personVO: PersonVO): PersonVO {
        logger.info("Updating person with name: ${personVO.firstName}")
        val entity = repository.findById(personVO.key)
            .orElseThrow{ResourceNotFoundException("No records found for this ID!")}

        entity.firstName = personVO.firstName
        entity.lastName = personVO.lastName
        entity.address = personVO.address
        entity.gender = personVO.gender

        repository.save(entity)

        val personVO = DozerMapper.parseObject(entity, PersonVO::class.java)
        val withSelRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelRel)
        return personVO
    }

    fun delete(id: Long) {
        logger.info("Deleting Person with id: ${id}")
        val entity = repository.findById(id)
            .orElseThrow{ResourceNotFoundException("No records found for this ID!")}

        repository.delete(entity)
    }

    fun createV2(personVO: PersonVOV2): PersonVOV2 {
        logger.info("Creating one person with name ${personVO.firstName}!")
        return mapper.mapEntityToVO(repository.save(mapper.mapVoToEntity(personVO)))
    }

}