package com.apiexcelpdf.apiexcelpdf.repository;


import com.apiexcelpdf.apiexcelpdf.model.CertificateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CertificateRepository extends JpaRepository<CertificateModel, UUID> {
}
