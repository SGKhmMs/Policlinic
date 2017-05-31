import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AppointmentOperationComponent } from './appointment-operation.component';
import { AppointmentOperationDetailComponent } from './appointment-operation-detail.component';
import { AppointmentOperationPopupComponent } from './appointment-operation-dialog.component';
import { AppointmentOperationDeletePopupComponent } from './appointment-operation-delete-dialog.component';

import { Principal } from '../../shared';

export const appointmentOperationRoute: Routes = [
    {
        path: 'appointment-operation',
        component: AppointmentOperationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.appointmentOperation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'appointment-operation/:id',
        component: AppointmentOperationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.appointmentOperation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const appointmentOperationPopupRoute: Routes = [
    {
        path: 'appointment-operation-new',
        component: AppointmentOperationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.appointmentOperation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'appointment-operation/:id/edit',
        component: AppointmentOperationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.appointmentOperation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'appointment-operation/:id/delete',
        component: AppointmentOperationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.appointmentOperation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
