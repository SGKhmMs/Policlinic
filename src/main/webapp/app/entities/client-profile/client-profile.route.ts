import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ClientProfileComponent } from './client-profile.component';
import { ClientProfileDetailComponent } from './client-profile-detail.component';
import { ClientProfilePopupComponent } from './client-profile-dialog.component';
import { ClientProfileDeletePopupComponent } from './client-profile-delete-dialog.component';

import { Principal } from '../../shared';

export const clientProfileRoute: Routes = [
    {
        path: 'client-profile',
        component: ClientProfileComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clientProfile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'client-profile/:id',
        component: ClientProfileDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clientProfile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clientProfilePopupRoute: Routes = [
    {
        path: 'client-profile-new',
        component: ClientProfilePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clientProfile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'client-profile/:id/edit',
        component: ClientProfilePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clientProfile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'client-profile/:id/delete',
        component: ClientProfileDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clientProfile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
