import {UserBean} from '../../bean/user';

export namespace UserAction {

  const PREFIX = '[User]';

  export class SetUser {
    static readonly type = `${PREFIX} Set User`;

    constructor(readonly user: UserBean | null, readonly token?: string | null) {
    }
  }

  export class Logout {
    static readonly type = `${PREFIX} Logout`;
  }

}
