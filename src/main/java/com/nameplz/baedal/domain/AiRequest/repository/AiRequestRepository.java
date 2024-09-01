package com.nameplz.baedal.domain.AiRequest.repository;

import com.nameplz.baedal.domain.AiRequest.domain.AiRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiRequestRepository extends JpaRepository<AiRequest, UUID> {

    List<AiRequest> findAllByUser_Username(String username);

}
