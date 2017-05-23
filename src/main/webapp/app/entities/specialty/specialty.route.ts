import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { SpecialtyComponent } from './specialty.component';
import { SpecialtyDetailComponent } from './specialty-detail.component';
import { SpecialtyPopupComponent } from './specialty-dialog.component';
import { SpecialtyDeletePopupComponent } from './specialty-delete-dialog.component';

import { Principal } from '../../shared';

export const specialtyRoute: Routes = [
    {
        path: 'specialty',
        component: SpecialtyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.specialty.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'specialty/:id',
        component: SpecialtyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.specialty.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const specialtyPopupRoute: Routes = [
    {
        path: 'specialty-new',
        component: SpecialtyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.specialty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'specialty/:id/edit',
        component: SpecialtyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.specialty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'specialty/:id/delete',
        component: SpecialtyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.specialty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
