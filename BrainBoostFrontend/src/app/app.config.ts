import {ApplicationConfig, importProvidersFrom} from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

import { routes } from './app.routes';
import {NgxsModule} from '@ngxs/store';
import {UserState} from './store/user/user.state';
import {FlashcardState} from './store/flashcard/flashcard.state';
import {ToastState} from './store/toast/toast.state';
import { authInterceptor } from './config/auth.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(withInterceptors([authInterceptor])),
    importProvidersFrom(
      NgxsModule.forRoot([UserState, FlashcardState, ToastState])
    )
  ]
};
