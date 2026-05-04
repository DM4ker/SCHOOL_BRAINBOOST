package DM4.brainboostbackend.service;

import DM4.brainboostbackend.bean.FlashCardBean;

import java.util.List;

public interface FlashCardService {

    boolean createFlashCard(FlashCardBean flashCardBean, Long flashCardSetId);

    boolean createFlashCardSet(List<FlashCardBean> flashCardBeans);

    FlashCardBean getFlashCardBean(Long flashCardId);

    List<FlashCardBean> getFlashCardBeansFromUser(Long userId);

    List<FlashCardBean> getFlashCardBeansFromFlashCardSetById(Long flashCardSetId);

    boolean deleteFlashCard(Long flashCardId);

    boolean deleteFlashCardSetById(Long flashCardSetId);
}

