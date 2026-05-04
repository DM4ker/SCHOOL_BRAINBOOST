package DM4.brainboostbackend.controller;

import DM4.brainboostbackend.bean.FlashCardBean;
import DM4.brainboostbackend.domain.UserEntity;
import DM4.brainboostbackend.service.SecurityService;
import DM4.brainboostbackend.worker.FlashCardWorker;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost"}, allowCredentials = "true")
@RestController
public class FlashCardController {

    private final FlashCardWorker flashCardWorker;
    private final SecurityService securityService;

    public FlashCardController(FlashCardWorker flashCardWorker, SecurityService securityService) {
        this.flashCardWorker = flashCardWorker;
        this.securityService = securityService;
    }

    @PostMapping("/flashcardset/{id}/flashcard/create/")
    @PreAuthorize("@flashCardWorker.isOwnerOfSet(#id, authentication.principal.id)")
    public ResponseEntity<Boolean> createFlashCard(@RequestBody FlashCardBean flashCardBean, @PathVariable Long id) {
        UserEntity currentUser = securityService.getCurrentUser();
        FlashCardBean updatedBean = new FlashCardBean(flashCardBean.id(), currentUser.getId(), flashCardBean.title(), flashCardBean.question(), flashCardBean.answer(), flashCardBean.lastLearned(), id);
        return ResponseEntity.ok(flashCardWorker.createFlashCard(updatedBean, id));
    }

    @PostMapping("/flashcard/create/")
    public ResponseEntity<Boolean> createFlashCard(@RequestBody FlashCardBean flashCardBean) {
        UserEntity currentUser = securityService.getCurrentUser();
        FlashCardBean updatedBean = new FlashCardBean(flashCardBean.id(), currentUser.getId(), flashCardBean.title(), flashCardBean.question(), flashCardBean.answer(), flashCardBean.lastLearned(), null);
        return ResponseEntity.ok(flashCardWorker.createFlashCard(updatedBean, null));
    }

    @PostMapping("/flashcardset/create/")
    public ResponseEntity<Boolean> createFlashCardSet(@RequestBody java.util.List<FlashCardBean> flashCardBeans) {
        UserEntity currentUser = securityService.getCurrentUser();
        List<FlashCardBean> updatedBeans = flashCardBeans.stream()
            .map(bean -> new FlashCardBean(bean.id(), currentUser.getId(), bean.title(), bean.question(), bean.answer(), bean.lastLearned(), null))
            .collect(Collectors.toList());
        return ResponseEntity.ok(flashCardWorker.createFlashCardSet(updatedBeans));
    }

    @GetMapping("/flashcard/{id}/")
    @PreAuthorize("@flashCardWorker.isOwnerOfCard(#id, authentication.principal.id)")
    public ResponseEntity<FlashCardBean> getFlashCardBean(@PathVariable Long id) {
        return ResponseEntity.ok(flashCardWorker.getFlashCardBean(id));
    }

    @GetMapping("/user/{id}/flashcards/")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<List<FlashCardBean>> getFlashCardBeansFromUser(@PathVariable Long id) {
        return ResponseEntity.ok(flashCardWorker.getFlashCardBeansFromUser(id));
    }

    @GetMapping("/flashcardset/{id}/flashcards/")
    @PreAuthorize("@flashCardWorker.isOwnerOfSet(#id, authentication.principal.id)")
    public ResponseEntity<List<FlashCardBean>> getFlashCardBeansFromFlashCardSetById(@PathVariable Long id) {
        return ResponseEntity.ok(flashCardWorker.getFlashCardBeansFromFlashCardSetById(id));
    }

    @DeleteMapping("/flashcard/{id}/")
    @PreAuthorize("@flashCardWorker.isOwnerOfCard(#id, authentication.principal.id)")
    public ResponseEntity<Boolean> deleteFlashCard(@PathVariable Long id) {
        return ResponseEntity.ok(flashCardWorker.deleteFlashCard(id));
    }

    @DeleteMapping("/flashcardset/{id}/")
    @PreAuthorize("@flashCardWorker.isOwnerOfSet(#id, authentication.principal.id)")
    public ResponseEntity<Boolean> deleteFlashCardSetById(@PathVariable Long id) {
        return ResponseEntity.ok(flashCardWorker.deleteFlashCardSetById(id));
    }

}

