package DM4.brainboostbackend.repository;

import DM4.brainboostbackend.domain.FlashCardEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashCardRepository extends JpaRepository<FlashCardEntity, Long> {
    List<FlashCardEntity> findByCreatorId(Long userId);
    List<FlashCardEntity> findByFlashCardSetId(Long flashCardSetId);
}

