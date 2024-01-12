package com.alja.patient.controller

import com.alja.errors.PatientError
import com.alja.patient.config.AppIntegrationTest
import com.alja.patient.dto.PatientRegisterDTO
import com.alja.patient.dto.PatientUpdateDTO
import com.alja.patient.fixtures.PatientFixtures
import com.alja.patient.model.mapper.PatientMapper
import com.alja.patient.model.repository.PatientRepository
import com.alja.patient.service.PatientDataValidationService
import com.alja.patient.service.PatientService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

import java.time.LocalDate
import java.time.Period

import static com.alja.patient.controller_resource.PatientAdminResource.RESOURCE_PATH
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
class PatientAdminControllerTest extends AppIntegrationTest {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private PatientService patientService

    @Autowired
    private PatientRepository patientRepository

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private PatientDataValidationService patientDataValidationService

    @Autowired
    private ObjectMapper objectMapper

    void cleanup() {
        patientRepository.deleteAll()
    }
//fixme fix test
    def 'should register new Patient'() {
        given:
            def firstName = 'Michal'
            def lastName = 'Lato'
            def birthDate = '2000-01-01'
            def socialSecurityNum = '12345678910'
            def phoneNumber = '123456789'
            def email = 'jand@mymail.com'
            def street = 'Wilcza'
            def houseNumber = '42'
            def postCode = '98-765'
            def city = 'Warszawa'
            def country = 'Polska'


        when:
            PatientRegisterDTO patientRegisterDTO
                    = PatientFixtures.createPatientRegisterDTOCustom(firstName, lastName, birthDate, socialSecurityNum,
                    phoneNumber, email, street, houseNumber, postCode, city, country)
            def result = this.mockMvc.perform(post(RESOURCE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(patientRegisterDTO)))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.firstName').value(firstName))
            result.andExpect(jsonPath('$.lastName').value(lastName))
    }

    def 'should not register new Patient when phoneNumber or email already exists'() {
        given:
            def firstName = 'Michal'
            def lastName = 'Lato'
            def birthDate = '2000-01-01'
            def socialSecurityNum = '12345678910'
            def phoneNumber = '123456789'
            def email = 'jand@mymail.com'
            def street = 'Wilcza'
            def houseNumber = '42'
            def postCode = '98-765'
            def city = 'Warszawa'
            def country = 'Polska'

            patientRepository.save(PatientFixtures.createPatientWithContactDetails(phoneNumber, email))

            PatientRegisterDTO patientRegisterDTO
                    = PatientFixtures.createPatientRegisterDTOCustom(firstName, lastName, birthDate, socialSecurityNum,
                    phoneNumber, email, street, houseNumber, postCode, city, country)

        when:
            def result = this.mockMvc.perform(post(RESOURCE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(patientRegisterDTO)))
        then:
            result.andExpect(status().isBadRequest())
            result.andExpect(jsonPath('$.message').value(PatientError.PATIENT_PHONE_NUMBER_ALREADY_EXISTS_ERROR.message))
    }


    def 'should get all Patients'() {
        given:
            def firstName = 'Michal'
            def lastName = 'Lato'
            def birthDate = '2000-01-01'
            def socialSecurityNum = '12345678910'
            def patientId = UUID.randomUUID() toString()
            patientRepository.save(PatientFixtures.createPatientWithFieldsAndUuid(firstName, lastName,
                    birthDate, socialSecurityNum, patientId))

        when:
            def result = this.mockMvc.perform(get(RESOURCE_PATH))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.[0].firstName').value(firstName))
            result.andExpect(jsonPath('$.[0].lastName').value(lastName))
            result.andExpect(jsonPath('$.[0].patientId').value(patientId))
    }

    def 'should get Patient by id'() {
        given:
            def firstName = 'Michal'
            def lastName = 'Lato'
            def birthDate = '2000-01-01'
            def socialSecurityNum = '12345678910'
            def patientId = UUID.randomUUID().toString()

            patientRepository.save(PatientFixtures.createPatientWithFieldsAndUuid(firstName, lastName,
                    birthDate, socialSecurityNum, patientId))

            def PATIENT_ID_PATH = "/" + patientId + "/dataFormat"

        when:
            def result = this.mockMvc.perform(get(RESOURCE_PATH + PATIENT_ID_PATH)
                    .contentType(MediaType.APPLICATION_JSON))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.firstName').value(firstName))
            result.andExpect(jsonPath('$.lastName').value(lastName))
            result.andExpect(jsonPath('$.patientId').value(patientId))
    }

    def 'should get Patient by id with details'() {
        given:
            def firstName = 'Michal'
            def lastName = 'Lato'
            def birthDate = '2000-01-01'
            def socialSecurityNum = '12345678910'
            def patientId = UUID.randomUUID().toString()
            def phoneNumber = '123456789'
            def email = 'jand@mymail.com'
            def street = 'Wilcza'
            def houseNumber = '42'
            def postCode = '98-765'
            def city = 'Warszawa'
            def country = 'Polska'

            def dateNow = LocalDate.now()
            def age = Period.between(LocalDate.parse(birthDate), dateNow).getYears();

            patientRepository.save(PatientFixtures.createPatientWithAllFieldsCustom(firstName, lastName,
                    birthDate, socialSecurityNum, patientId,
                    phoneNumber, email,
                    street, houseNumber, postCode, city, country))

            def PATIENT_ID_PATH = "/" + patientId + "/dataFormat"
            def dataFormat = "DETAILS"

        when:
            def result = this.mockMvc.perform(get(RESOURCE_PATH + PATIENT_ID_PATH)
                    .param("dataFormat", dataFormat)
                    .contentType(MediaType.APPLICATION_JSON))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.firstName').value(firstName))
            result.andExpect(jsonPath('$.lastName').value(lastName))
            result.andExpect(jsonPath('$.patientId').value(patientId))
            result.andExpect(jsonPath('$.birthDate').value(birthDate))
            result.andExpect(jsonPath('$.age').value(age))
            result.andExpect(jsonPath('$.socialSecurityNumber').value(socialSecurityNum))
            result.andExpect(jsonPath('$.contactDetails.phoneNumber').value(phoneNumber))
            result.andExpect(jsonPath('$.contactDetails.email').value(email))
            result.andExpect(jsonPath('$.address.street').value(street))
            result.andExpect(jsonPath('$.address.houseNumber').value(houseNumber))
            result.andExpect(jsonPath('$.address.postCode').value(postCode))
            result.andExpect(jsonPath('$.address.city').value(city))
            result.andExpect(jsonPath('$.address.country').value(country))
    }
//fixme fix test
    def 'should get Patient by id with visits'() {
        //todo update test with visits
        given:
            def firstName = 'Michal'
            def lastName = 'Lato'
            def birthDate = '2000-01-01'
            def socialSecurityNum = '12345678910'
            def patientId = UUID.randomUUID().toString()

            patientRepository.save(PatientFixtures.createPatientWithFieldsAndUuid(firstName, lastName,
                    birthDate, socialSecurityNum, patientId))

            def PATIENT_ID_PATH = "/" + patientId + "/dataFormat"
            def dataFormat = "VISITS"

        when:
            def result = this.mockMvc.perform(get(RESOURCE_PATH + PATIENT_ID_PATH)
                    .param("dataFormat", dataFormat)
                    .contentType(MediaType.APPLICATION_JSON))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.firstName').value(firstName))
            result.andExpect(jsonPath('$.lastName').value(lastName))
            result.andExpect(jsonPath('$.patientId').value(patientId))
            result.andExpect(jsonPath('$.visitsId.[0]').doesNotExist())
    }

    def 'should not get Patient by id when not exists'() {
        given:
            def patientId = UUID.randomUUID().toString()

            def PATIENT_ID_PATH = "/" + patientId + "/dataFormat"

        when:
            def result = this.mockMvc.perform(get(RESOURCE_PATH + PATIENT_ID_PATH)
                    .contentType(MediaType.APPLICATION_JSON))

        then:
            result.andExpect(status().isNotFound())
            result.andExpect(jsonPath('$.message').value(PatientError.PATIENT_NOT_FOUND_ERROR.message))
    }

    def 'should update Patient'() {
        given:
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def birthDate = '2000-01-01'
            def socialSecurityNum = '12345678910'

            def firstNameUpdated = 'John'
            def lastNameUpdated = 'Good'
            def patientId = UUID.randomUUID().toString()
            def socialSecurityNumUpdated = '12345678911'

            patientRepository.save(PatientFixtures.createPatientWithFieldsAndUuid(firstName, lastName,
                    birthDate, socialSecurityNum, patientId))

            PatientUpdateDTO patientUpdateDTO
                    = PatientFixtures.createPatientUpdateWithFields(firstNameUpdated, lastNameUpdated,
                    birthDate, socialSecurityNumUpdated)

            def PATIENT_ID_PATH = "/" + patientId

        when:
            def result = this.mockMvc.perform(put(RESOURCE_PATH + PATIENT_ID_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(patientUpdateDTO)))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.firstName').value(firstNameUpdated))
            result.andExpect(jsonPath('$.lastName').value(lastNameUpdated))
            result.andExpect(jsonPath('$.patientId').value(patientId))
    }

    def 'should delete Patient by id'() {
        given:
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def patientId = UUID.randomUUID().toString()

            patientRepository.save(PatientFixtures.createPatientWithNameAndId(firstName, lastName, patientId))

            def PATIENT_ID_PATH = "/" + patientId

        when:
            def result = this.mockMvc.perform(delete(RESOURCE_PATH + PATIENT_ID_PATH))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.firstName').value(firstName))
            result.andExpect(jsonPath('$.lastName').value(lastName))
            result.andExpect(jsonPath('$.patientId').value(patientId))
    }

    def 'should not delete Patient by id when not exists'() {
        given:
            def patientId = UUID.randomUUID().toString()

            def PATIENT_ID_PATH = "/" + patientId

        when:
            def result = this.mockMvc.perform(delete(RESOURCE_PATH + PATIENT_ID_PATH))

        then:
            result.andExpect(status().isNotFound())
            result.andExpect(jsonPath('$.message').value(PatientError.PATIENT_NOT_FOUND_ERROR.message))
    }
}
