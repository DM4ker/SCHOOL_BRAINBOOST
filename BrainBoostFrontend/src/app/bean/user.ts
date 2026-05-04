export interface UserBean {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
}

export interface LoginBean {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  user: UserBean;
}

export interface RegisterBean extends Partial<UserBean> {
  password: string;
}

export interface UpdateUserBean extends RegisterBean {
}