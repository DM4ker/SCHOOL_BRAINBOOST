import {Component} from '@angular/core';
import {Store} from '@ngxs/store';
import {UserService} from '../../../service/rest/user/user.service';
import {BehaviorSubject, combineLatest, firstValueFrom, map} from 'rxjs';
import {LoginBean, LoginResponse} from '../../../bean/user';
import {UserAction} from '../../../store/user/user.actions';
import {Router, RouterLink} from '@angular/router';
import {ToastAction} from '../../../store/toast/toast.action';
import {ToastType} from '../../../bean/ToastBean';

@Component({
  selector: 'app-login',
  imports: [
    RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {

  private readonly userNameSubject$ = new BehaviorSubject("");
  public readonly userName$ = this.userNameSubject$.asObservable();

  private readonly passwordSubject$ = new BehaviorSubject("");
  public readonly password$ = this.passwordSubject$.asObservable();


  public readonly user$ = combineLatest([this.userName$, this.password$]).pipe(
    map(([userName, password]): LoginBean => ({
      username: userName,
      password: password
      })
    ));

  constructor(private readonly store: Store,
              private readonly userService: UserService,
              private readonly router: Router) {
  }

  public nextUserName(event: Event) {
    this.userNameSubject$.next((event.target as HTMLInputElement).value);
  }

  public nextPassword(event: Event) {
    this.passwordSubject$.next((event.target as HTMLInputElement).value);
  }

  public async login$() {
    const user = await firstValueFrom(this.user$);

    if(user.username.trim() === '') {
      this.store.dispatch(new ToastAction.ShowToast({
        message: `Username cannot be empty.`,
        type: ToastType.ERROR,
        duration: 3000
      }))
      return;
    }

    if(user.password.trim() === '') {
      this.store.dispatch(new ToastAction.ShowToast({
        message: `Password cannot be empty.`,
        type: ToastType.ERROR,
        duration: 3000
      }))
      return;
    }

    try {
      const response = await firstValueFrom(this.userService.login$(user));
      if (response) {
        this.store.dispatch(new UserAction.SetUser(response.user, response.token));
      }
      this.store.dispatch(new ToastAction.ShowToast({
        message: `Welcome back, ${user.username}!`,
        type: ToastType.SUCCESS,
        duration: 3000
      }))
      this.router.navigateByUrl(this.router.createUrlTree(['/']));
    } catch (error: any) {
      let errorMessage = 'Login failed. Please check your credentials.';
      
      // Extract error message from different possible error structures
      if (error?.error?.message) {
        errorMessage = error.error.message;
      } else if (error?.message) {
        errorMessage = error.message;
      } else if (typeof error === 'string') {
        errorMessage = error;
      }
      
      this.store.dispatch(new ToastAction.ShowToast({
        message: errorMessage,
        type: ToastType.ERROR,
        duration: 3000
      }))
    }
  }

}
