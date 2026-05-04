package DM4.brainboostbackend.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "flash_card_set_entity")
public class FlashCardSetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private UserEntity creator;

    @OneToMany(mappedBy = "flashCardSet")
    private List<FlashCardEntity> flashCards;

    public FlashCardSetEntity() {}

}

