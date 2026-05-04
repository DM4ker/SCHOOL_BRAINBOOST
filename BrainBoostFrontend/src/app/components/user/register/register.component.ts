import { Component } from '@angular/core';
import {BehaviorSubject, combineLatest, firstValueFrom, map} from 'rxjs';
import {RegisterBean} from '../../../bean/user';
import {UserService} from '../../../service/rest/user/user.service';
import {Store} from '@ngxs/store';
import {UserAction} from '../../../store/user/user.actions';
import {Router} from '@angular/router';
import {ToastAction} from '../../../store/toast/toast.action';
import {ToastType} from '../../../bean/ToastBean';

@Component({
  selector: 'app-register',
  imports: [],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  private readonly usernameSubject$ = new BehaviorSubject("");
  public readonly username$ = this.usernameSubject$.asObservable();

  private readonly passwordSubject$ = new BehaviorSubject("");
  public readonly password$ = this.passwordSubject$.asObservable();

  private readonly firstNameSubject$ = new BehaviorSubject("");
  public readonly firstName$ = this.firstNameSubject$.asObservable();

  private readonly lastNameSubject$ = new BehaviorSubject("");
  public readonly lastName$ = this.lastNameSubject$.asObservable();

  public readonly registerUserBean$ = combineLatest([
    this.username$,
    this.password$,
    this.firstName$,
    this.lastName$
  ]).pipe(
    map(([username, password, firstName, lastName]): RegisterBean => ({
      username: username,
      password: password,
      firstName: firstName,
      lastName: lastName
    }))
  );

  constructor(private readonly userService: UserService,
              private readonly store: Store,
              private readonly router: Router) {
  }

  private validatePassword(password: string): string[] {
    const missing: string[] = [];
    if (password.length < 8) missing.push('at least 8 characters');
    if (!/[A-Z]/.test(password)) missing.push('an uppercase letter');
    if (!/[a-z]/.test(password)) missing.push('a lowercase letter');
    if (!/\d/.test(password)) missing.push('a number');
    if (!/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(password)) missing.push('a special character');
    return missing;
  }

  public nextUsername(event: Event) {
    this.usernameSubject$.next((event.target as HTMLInputElement).value);
  }

  public nextPassword(event: Event) {
    this.passwordSubject$.next((event.target as HTMLInputElement).value);
  }

  public nextFirstName(event: Event) {
    this.firstNameSubject$.next((event.target as HTMLInputElement).value);
  }

  public nextLastName(event: Event) {
    this.lastNameSubject$.next((event.target as HTMLInputElement).value);
  }

  public async register$() {
    const registerBean = await firstValueFrom(this.registerUserBean$);

    if(registerBean.firstName?.trim() === '') {
      this.store.dispatch(new ToastAction.ShowToast({
        message: 'First name cannot be empty.',
        type: ToastType.ERROR,
        duration: 3000
      }))
      return;
    }

    if(registerBean.lastName?.trim() === '') {
      this.store.dispatch(new ToastAction.ShowToast({
        message: 'Last name cannot be empty.',
        type: ToastType.ERROR,
        duration: 3000
      }))
      return;
    }

    if(registerBean.username?.trim() === '') {
      this.store.dispatch(new ToastAction.ShowToast({
        message: 'Username cannot be empty.',
        type: ToastType.ERROR,
        duration: 3000
      }))
      return;
    }

    if(registerBean.password?.trim() === '') {
      this.store.dispatch(new ToastAction.ShowToast({
        message: 'Password cannot be empty.',
        type: ToastType.ERROR,
        duration: 3000
      }))
      return;
    }

    const missingRequirements = this.validatePassword(registerBean.password);
    if (missingRequirements.length > 0) {
      this.store.dispatch(new ToastAction.ShowToast({
        message: 'Password must include: ' + missingRequirements.join(', '),
        type: ToastType.ERROR,
        duration: 3000
      }));
      return;
    }

    try {
      const user = await firstValueFrom(this.userService.register$(registerBean));
      this.store.dispatch(new UserAction.SetUser(user));
      this.router.navigateByUrl(this.router.createUrlTree(['/']));
    } catch (error: any) {
      let errorMessage = 'Registration failed. Please check your input.';
      
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
      }));
    }
  }
}
