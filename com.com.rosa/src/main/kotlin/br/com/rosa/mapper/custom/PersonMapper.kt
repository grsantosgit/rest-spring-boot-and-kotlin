package br.com.rosa.mapper.custom

import br.com.rosa.data.vo.v2.PersonVO
import br.com.rosa.model.Person
import org.springframework.stereotype.Service
import java.util.Date

@Service
class PersonMapper {

    fun mapEntityToVO(person: Person): PersonVO{
        val vo = PersonVO()

        vo.id =  person.id
        vo.address = person.address
        vo.birthDay = Date()
        vo.firstName = person.firstName
        vo.lastName = person.lastName
        vo.gender = person.gender

        return vo

    }

    fun mapVoToEntity(vo: PersonVO): Person{
        val person = Person()

        person.id =  vo.id
        person.address = vo.address
        person.firstName = vo.firstName
        person.lastName = vo.lastName
        person.gender = vo.gender

        return person
    }

}