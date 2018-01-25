import { RegisterComponent } from './../account/register/register.component';
import { Route } from '@angular/router';

import { HomeComponent } from './';

export const HOME_ROUTE: Route = {
    path: '',
    // component: HomeComponent,
    component: RegisterComponent,
    data: {
        authorities: [],
        pageTitle: 'Welcome, Teammate!'
    }
};
