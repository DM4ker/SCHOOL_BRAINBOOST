package DM4.brainboostbackend.repository;

import DM4.brainboostbackend.domain.FlashCardSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashCardSetRepository extends JpaRepository<FlashCardSetEntity, Long> {
}

