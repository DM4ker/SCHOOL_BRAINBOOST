import { Injectable } from '@angular/core';
import {RestService} from '../rest.service';
import {LoginBean, LoginResponse, RegisterBean, UpdateUserBean, UserBean} from '../../../bean/user';
import {Store} from '@ngxs/store';
import {UserAction} from '../../../store/user/user.actions';

@Injectable({
  providedIn: 'root',
})
export class UserService {


  constructor(private readonly restService: RestService,
              private readonly store: Store) {

  }

  public login$(loginBean: LoginBean) {
    return this.restService.post$<LoginResponse>('/user/login', loginBean);
  }

  public register$(registerBean: RegisterBean) {
    return this.restService.post$<UserBean>('/user/register', registerBean);
  }

  public logout$() {
    return this.store.dispatch(new UserAction.Logout)
  }

  public patch$(id: number, patchBean: Partial<UpdateUserBean>) {
    return this.restService.patch$<LoginResponse>('/user/' + id, patchBean);
  }
  
}
