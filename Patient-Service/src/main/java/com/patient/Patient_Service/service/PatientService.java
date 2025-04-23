package com.patient.Patient_Service.service;

import com.patient.Patient_Service.dto.PatientRequestDTO;
import com.patient.Patient_Service.dto.PatientResponseDTO;
import com.patient.Patient_Service.exception.EmailAlreadyExistsException;
import com.patient.Patient_Service.mapper.PatientMapper;
import com.patient.Patient_Service.model.Patient;
import com.patient.Patient_Service.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private PatientRepository patientRepository;


    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "A patient with this email " + "already exists"
                            + patientRequestDTO.getEmail());
        }

        Patient newPatient = patientRepository.save(
                PatientMapper.toModel(patientRequestDTO));

//        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(),
//                newPatient.getName(), newPatient.getEmail());
//
//        kafkaProducer.sendEvent(newPatient);

        return PatientMapper.toDTO(newPatient);
    }
}
