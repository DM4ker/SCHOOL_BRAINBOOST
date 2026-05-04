package DM4.brainboostbackend.worker;

import DM4.brainboostbackend.bean.FlashCardBean;
import DM4.brainboostbackend.domain.FlashCardEntity;
import DM4.brainboostbackend.domain.FlashCardSetEntity;
import DM4.brainboostbackend.domain.UserEntity;
import DM4.brainboostbackend.repository.FlashCardRepository;
import DM4.brainboostbackend.repository.FlashCardSetRepository;
import DM4.brainboostbackend.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlashCardWorker {

    private final FlashCardRepository flashCardRepository;
    private final FlashCardSetRepository flashCardSetRepository;
    private final UserRepository userRepository;

    public FlashCardWorker(FlashCardRepository flashCardRepository, FlashCardSetRepository flashCardSetRepository, UserRepository userRepository) {
        this.flashCardRepository = flashCardRepository;
        this.flashCardSetRepository = flashCardSetRepository;
        this.userRepository = userRepository;
    }

    public FlashCardBean getFlashCardBean(Long flashCardId) {
        return FlashCardBean.from(flashCardRepository.getReferenceById(flashCardId));
    }

    public List<FlashCardBean> getFlashCardBeansFromUser(Long userId) {
        return this.flashCardRepository.findByCreatorId(userId).stream().map(FlashCardBean::from).collect(Collectors.toList());
    }

    public List<FlashCardBean> getFlashCardBeansFromFlashCardSetById(Long flashCardSetId) {
        return this.flashCardRepository.findByFlashCardSetId(flashCardSetId).stream().map(FlashCardBean::from).collect(Collectors.toList());
    }

    public boolean deleteFlashCard(Long flashCardId) {
        flashCardRepository.deleteById(flashCardId);
        return true;
    }

    public boolean deleteFlashCardSetById(Long flashCardSetId) {
        List<FlashCardEntity> flashCardEntities = flashCardRepository.findByFlashCardSetId(flashCardSetId);
        flashCardRepository.deleteAll(flashCardEntities);
        flashCardSetRepository.deleteById(flashCardSetId);
        return true;
    }

    public boolean createFlashCard(FlashCardBean flashCardBean, Long flashCardSetId) {
        UserEntity userEntity = userRepository.getReferenceById(flashCardBean.userId());

        FlashCardEntity flashCardEntity = new FlashCardEntity();
        flashCardEntity.setTitle(flashCardBean.title());
        flashCardEntity.setQuestion(flashCardBean.question());
        flashCardEntity.setAnswer(flashCardBean.answer());
        flashCardEntity.setCreator(userEntity);
        FlashCardSetEntity flashCardSetEntity;

        if(flashCardSetId == null){
            flashCardSetEntity = new FlashCardSetEntity();
            flashCardSetEntity.setCreator(userEntity);
            flashCardSetRepository.save(flashCardSetEntity);

        } else {
            flashCardSetEntity = flashCardSetRepository.getReferenceById(flashCardSetId);
        }

        flashCardEntity.setFlashCardSet(flashCardSetEntity);
        flashCardRepository.save(flashCardEntity);

        return true;
    }

    public boolean createFlashCardSet(List<FlashCardBean> flashCardBeans) {
        if (flashCardBeans.isEmpty()) return false;
        UserEntity userEntity = userRepository.getReferenceById(flashCardBeans.get(0).userId());
        FlashCardSetEntity flashCardSetEntity = new FlashCardSetEntity();
        flashCardSetEntity.setCreator(userEntity);
        flashCardSetRepository.save(flashCardSetEntity);

        for (FlashCardBean flashCardBean : flashCardBeans) {
            FlashCardEntity flashCardEntity = new FlashCardEntity();
            flashCardEntity.setTitle(flashCardBean.title());
            flashCardEntity.setQuestion(flashCardBean.question());
            flashCardEntity.setAnswer(flashCardBean.answer());
            flashCardEntity.setCreator(userEntity);
            flashCardEntity.setFlashCardSet(flashCardSetEntity);
            flashCardRepository.save(flashCardEntity);
        }
        return true;
    }

    public boolean isOwnerOfCard(Long cardId, Long userId) {
        FlashCardEntity card = flashCardRepository.findById(cardId).orElse(null);
        return card != null && card.getCreator().getId().equals(userId);
    }

    public boolean isOwnerOfSet(Long setId, Long userId) {
        FlashCardSetEntity set = flashCardSetRepository.findById(setId).orElse(null);
        return set != null && set.getCreator() != null && set.getCreator().getId().equals(userId);
    }
}

