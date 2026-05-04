import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngxs/store';
import { BehaviorSubject, firstValueFrom, Observable } from 'rxjs';
import { ToastType } from '../../../bean/ToastBean';
import { UpdateUserBean, UserBean } from '../../../bean/user';
import { UserService } from '../../../service/rest/user/user.service';
import { ToastAction } from '../../../store/toast/toast.action';
import { UserAction } from '../../../store/user/user.actions';
import { UserState } from '../../../store/user/user.state';

@Component({
  selector: 'app-edit',
  imports: [],
  templateUrl: './edit.component.html',
  styleUrl: './edit.component.scss'
})
export class EditComponent implements OnInit {
  private readonly usernameSubject$ = new BehaviorSubject<string | null>(null);
  public readonly username$ = this.usernameSubject$.asObservable();

  private readonly passwordSubject$ = new BehaviorSubject<string | null>(null);
  public readonly password$ = this.passwordSubject$.asObservable();

  private readonly firstNameSubject$ = new BehaviorSubject<string | null>(null);
  public readonly firstName$ = this.firstNameSubject$.asObservable();

  private readonly lastNameSubject$ = new BehaviorSubject<string | null>(null);
  public readonly lastName$ = this.lastNameSubject$.asObservable();

  private readonly user$: Observable<UserBean | null> = inject(Store).select(UserState.getUser);

  constructor(private readonly userService: UserService,
              private readonly store: Store,
              private readonly router: Router) {
  }

  ngOnInit(): void {
    // Initialize form with current user data
    this.user$.subscribe(user => {
      if (user) {
        // Don't auto-populate, let user choose what to update
      }
    });
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

  private buildPatchBean(): Partial<UpdateUserBean> {
    const patch: Partial<UpdateUserBean> = {};

    const username = this.usernameSubject$.value;
    if (username !== null && username !== '') {
      patch.username = username;
    }

    const password = this.passwordSubject$.value;
    if (password !== null && password !== '') {
      patch.password = password;
    }

    const firstName = this.firstNameSubject$.value;
    if (firstName !== null && firstName !== '') {
      patch.firstName = firstName;
    }

    const lastName = this.lastNameSubject$.value;
    if (lastName !== null && lastName !== '') {
      patch.lastName = lastName;
    }

    return patch;
  }

  public async patch$() {
    const patchBean = this.buildPatchBean();
    const currentUser = await firstValueFrom(this.user$);

    if (!currentUser) {
      this.store.dispatch(new ToastAction.ShowToast({
        message: 'No user is currently logged in.',
        type: ToastType.ERROR,
        duration: 3000
      }))
      return;
    }

    if (Object.keys(patchBean).length === 0) {
      this.store.dispatch(new ToastAction.ShowToast({
        message: 'No changes to save.',
        type: ToastType.WARNING,
        duration: 3000
      }))
      return;
    }

    // Validate password if provided
    const password = this.passwordSubject$.value;
    if (password !== null && password !== '') {
      const missingRequirements = this.validatePassword(password);
      if (missingRequirements.length > 0) {
        this.store.dispatch(new ToastAction.ShowToast({
          message: 'Password must include: ' + missingRequirements.join(', '),
          type: ToastType.ERROR,
          duration: 3000
        }));
        return;
      }
    }

    try {
      const response = await firstValueFrom(this.userService.patch$(currentUser.id, patchBean));
      if (response) {
        this.store.dispatch(new UserAction.SetUser(response.user, response.token));
      }
      this.store.dispatch(new ToastAction.ShowToast({
        message: 'Profile updated successfully.',
        type: ToastType.SUCCESS,
        duration: 3000
      }))
      this.router.navigateByUrl(this.router.createUrlTree(['/'])); 
    } catch (error: any) {
      let errorMessage = 'Failed to update profile.';
      
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


