import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ClinicComponent } from './clinic.component';
import { ClinicDetailComponent } from './clinic-detail.component';
import { ClinicPopupComponent } from './clinic-dialog.component';
import { ClinicDeletePopupComponent } from './clinic-delete-dialog.component';

import { Principal } from '../../shared';

export const clinicRoute: Routes = [
    {
        path: 'clinic',
        component: ClinicComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinic.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'clinic/:id',
        component: ClinicDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinic.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clinicPopupRoute: Routes = [
    {
        path: 'clinic-new',
        component: ClinicPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinic.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clinic/:id/edit',
        component: ClinicPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinic.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clinic/:id/delete',
        component: ClinicDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinic.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
