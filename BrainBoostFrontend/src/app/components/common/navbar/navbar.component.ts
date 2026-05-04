import {Component, inject} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {Store} from '@ngxs/store';
import {UserState} from '../../../store/user/user.state';
import {AsyncPipe} from '@angular/common';
import {firstValueFrom} from 'rxjs';
import {UserAction} from '../../../store/user/user.actions';
import {ToastAction} from '../../../store/toast/toast.action';
import {ToastType} from '../../../bean/ToastBean';

@Component({
  selector: 'app-navbar',
  imports: [
    RouterLink,
    AsyncPipe
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {

  public isLoggedIn$ = inject(Store).select(UserState.isLoggedIn);
  public loggedInUser$ = inject(Store).select(UserState.getUser);

  constructor(private readonly store: Store,
              private readonly router: Router
  ) {
  }

  public async logout$() {
    return firstValueFrom(this.store.dispatch(new UserAction.Logout)).then(() => this.router.navigateByUrl(this.router.createUrlTree(['/'])))
  }

  public async copyLinkToClipboard() {
    const user = await firstValueFrom(this.loggedInUser$);
    if(!user) {
      throw new Error("User not logged in");
    }
    const link = `${window.location.origin}/flashcard/learn?userId=${user.id}`;
    await navigator.clipboard.writeText(link);
    return firstValueFrom(this.store.dispatch(new ToastAction.ShowToast({
      message: 'Link copied to clipboard!',
      type: ToastType.SUCCESS,
      duration: 3000
    })))
  }
}
