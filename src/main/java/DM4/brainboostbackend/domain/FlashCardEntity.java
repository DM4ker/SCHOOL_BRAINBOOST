package DM4.brainboostbackend.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "flash_card_entity")
public class FlashCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String question;

    private String answer;

    private Long lastLearned;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private UserEntity creator;

    @ManyToOne
    @JoinColumn(name = "flash_card_set_id")
    private FlashCardSetEntity flashCardSet;

    public FlashCardEntity() {}

}

