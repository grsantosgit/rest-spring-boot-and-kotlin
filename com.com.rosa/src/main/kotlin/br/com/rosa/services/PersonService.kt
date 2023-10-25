package br.com.rosa.services

import br.com.rosa.data.vo.v1.PersonVO
import br.com.rosa.data.vo.v2.PersonVO as PersonVOV2
import br.com.rosa.exceptions.ResourceNotFoundException
import br.com.rosa.mapper.DozerMapper
import br.com.rosa.mapper.custom.PersonMapper
import br.com.rosa.model.Person
import br.com.rosa.repositories.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
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
        logger.info("Finding one Person!")

        val person = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }

        return DozerMapper.parseObject(person, PersonVO::class.java)

    }

    fun findAll(): List<PersonVO> {
        logger.info("Finding all People!")
        val persons = repository.findAll()

        return DozerMapper.parseListObjects(persons, PersonVO::class.java)
    }

    fun create(personVO: PersonVO): PersonVO {
        logger.info("Creating one Person with name: ${personVO.firstName}")

        val person = DozerMapper.parseObject(personVO, Person::class.java)
        repository.save(person)

       return DozerMapper.parseObject(person, PersonVO::class.java)
    }

    fun update(personVO: PersonVO): PersonVO {
        logger.info("Updating person with name: ${personVO.firstName}")
        val entity = repository.findById(personVO.id)
            .orElseThrow{ResourceNotFoundException("No records found for this ID!")}

        entity.firstName = personVO.firstName
        entity.lastName = personVO.lastName
        entity.address = personVO.address
        entity.gender = personVO.gender

       repository.save(entity)

        return DozerMapper.parseObject(entity, PersonVO::class.java)
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