import {Component, inject} from '@angular/core';
import {BehaviorSubject, firstValueFrom} from 'rxjs';
import {FlashcardService} from '../../../service/rest/flashcard/flashcard.service';
import {Store} from '@ngxs/store';
import {UserState} from '../../../store/user/user.state';
import {FlashcardBean} from '../../../bean/flashcard';
import {Router} from '@angular/router';

@Component({
  selector: 'app-flashcard-create',
  imports: [],
  templateUrl: './flashcard-create.component.html',
  styleUrl: './flashcard-create.component.scss'
})
export class FlashcardCreateComponent {

  private readonly flashCardTitleSubject = new BehaviorSubject("");
  public readonly flashCardTitle$ = this.flashCardTitleSubject.asObservable();

  private readonly flashCardQuestionSubject = new BehaviorSubject("");
  public readonly flashCardQuestion$ = this.flashCardQuestionSubject.asObservable();

  public readonly flashCardAnswerSubject = new BehaviorSubject("");
  public readonly flashCardAnswer$ = this.flashCardAnswerSubject.asObservable();

  private readonly user$ = inject(Store).select(UserState.getUser);


  constructor(private readonly flashCardService: FlashcardService,
              private readonly router: Router) {
  }

  public nextFlashCardTitle(event: Event) {
    this.flashCardTitleSubject.next((event.target as HTMLInputElement).value);
  }

  public nextFlashCardQuestion(event: Event) {
    this.flashCardQuestionSubject.next((event.target as HTMLInputElement).value);
  }

  public nextFlashCardAnswer(event: Event) {
    this.flashCardAnswerSubject.next((event.target as HTMLInputElement).value);
  }

  public async createFlashCard$() {
    const user = await firstValueFrom(this.user$);
    if(!user) {
      throw new Error("User not logged in");
    }
    const title = await firstValueFrom(this.flashCardTitle$);
    const question = await firstValueFrom(this.flashCardQuestion$);
    const answer = await firstValueFrom(this.flashCardAnswer$);

    const flashCardBean: Partial<FlashcardBean> = {
      title: title,
      question: question,
      answer: answer,
      userId: user.id
    }

    return firstValueFrom(this.flashCardService.createFlashcard$(flashCardBean)).then(() => {
      this.router.navigateByUrl(this.router.createUrlTree(['/flashcard/overview']));
    });
  }
}
