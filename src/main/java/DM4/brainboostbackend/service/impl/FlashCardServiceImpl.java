package DM4.brainboostbackend.service.impl;

import DM4.brainboostbackend.bean.FlashCardBean;
import DM4.brainboostbackend.service.FlashCardService;
import DM4.brainboostbackend.worker.FlashCardWorker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlashCardServiceImpl implements FlashCardService {
    private final FlashCardWorker flashCardWorker;

    public FlashCardServiceImpl(FlashCardWorker flashCardWorker) {
        this.flashCardWorker = flashCardWorker;
    }

    @Override
    @Transactional
    public boolean createFlashCard(FlashCardBean flashCardBean, Long flashCardSetId) {
       return flashCardWorker.createFlashCard(flashCardBean, flashCardSetId);
    }

    @Override
    @Transactional
    public boolean createFlashCardSet(List<FlashCardBean> flashCardBeans) {
        return flashCardWorker.createFlashCardSet(flashCardBeans);
    }

    @Override
    public FlashCardBean getFlashCardBean(Long flashCardId) {
        return flashCardWorker.getFlashCardBean(flashCardId);
    }

    @Override
    public List<FlashCardBean> getFlashCardBeansFromUser(Long userId) {
        return flashCardWorker.getFlashCardBeansFromUser(userId);
    }

    @Override
    public List<FlashCardBean> getFlashCardBeansFromFlashCardSetById(Long flashCardSetId) {
        return flashCardWorker.getFlashCardBeansFromFlashCardSetById(flashCardSetId);
    }

    @Override
    @Transactional
    public boolean deleteFlashCard(Long flashCardId) {
        return flashCardWorker.deleteFlashCard(flashCardId);
    }

    @Override
    @Transactional
    public boolean deleteFlashCardSetById(Long flashCardSetId) {
        return flashCardWorker.deleteFlashCardSetById(flashCardSetId);
    }
}

