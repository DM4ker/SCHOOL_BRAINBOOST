import {Injectable} from '@angular/core';
import {Action, Selector, State, StateContext, StateToken} from '@ngxs/store';
import {UserAction} from './user.actions';
import {UserBean} from '../../bean/user';

export interface UserModel {
  user: UserBean | null;
  token: string | null;
}

const DEFAULTS: UserModel = {
  user: null,
  token: null
}

const USER_TOKEN = new StateToken<UserModel>('user');

@State<UserModel>({
  name: USER_TOKEN,
  defaults: DEFAULTS
})
@Injectable()
export class UserState {

  constructor() {
  }

  @Selector()
  static getUser(state: UserModel) {
    return state.user;
  }

  @Selector()
  static isLoggedIn(state: UserModel) {
    return state.user !== null;
  }

  @Selector()
  static getToken(state: UserModel) {
    return state.token;
  }

  @Action(UserAction.SetUser)
  setUser(ctx: StateContext<UserModel>, action: UserAction.SetUser) {
    ctx.setState({
      user: action.user,
      token: action.token ?? null,
    });
  }

  @Action(UserAction.Logout)
  logout(ctx: StateContext<UserModel>) {
    ctx.setState({
      user: null,
      token: null,
    });
  }

}
