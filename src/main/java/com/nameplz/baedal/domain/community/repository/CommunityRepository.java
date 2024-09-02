package com.nameplz.baedal.domain.community.repository;

import com.nameplz.baedal.domain.community.domain.Community;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, UUID> {

    Optional<Community> findByIdAndDeletedAtIsNull(UUID communityId);

    List<Community> findAllByDeletedAtIsNull(Pageable pageable);
}

