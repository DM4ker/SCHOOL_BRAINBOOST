import { Routes } from '@angular/router';
import {ErrorComponent} from './components/common/error/error.component';
import {LoginComponent} from './components/user/login/login.component';
import {RegisterComponent} from './components/user/register/register.component';
import {redirectToHomeGuardIfLoggedIn} from './guard/redirect-to-home-guard-if-logged.in';
import {FlashcardCreateComponent} from './components/flashcard/flashcard-create/flashcard-create.component';
import {FlashcardOverviewComponent} from './components/flashcard/flashcard-overview/flashcard-overview.component';
import {FlashcardLearnComponent} from './components/flashcard/flashcard-learn/flashcard-learn.component';
import {HomeComponent} from './components/common/home/home.component';
import {redirectToHomeGuardIfNotLoggedIn} from './guard/redirect-to-home-guard-if-not-logged.in';
import { EditComponent } from './components/user/edit/edit.component';


export const routes: Routes = [

  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [
      redirectToHomeGuardIfLoggedIn
    ]
  },
  {
    path:'register',
    component: RegisterComponent,
    canActivate: [
      redirectToHomeGuardIfLoggedIn
    ]
  },
  {
    path: 'profile',
    component: EditComponent,
    canActivate: [
      redirectToHomeGuardIfNotLoggedIn
    ]
  },

  {
    path: 'flashcard',



    children: [
      {
        path: 'create',
        component: FlashcardCreateComponent,
        canActivate: [
          redirectToHomeGuardIfNotLoggedIn
        ],
      },
      {
        path: 'overview',
        component: FlashcardOverviewComponent
      },
      {
        path: 'learn',
        component: FlashcardLearnComponent
      }
    ]
  },
  {
    path: '**', component: ErrorComponent
  },
];
