import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ServiceOnAppointmentComponent } from './service-on-appointment.component';
import { ServiceOnAppointmentDetailComponent } from './service-on-appointment-detail.component';
import { ServiceOnAppointmentPopupComponent } from './service-on-appointment-dialog.component';
import { ServiceOnAppointmentDeletePopupComponent } from './service-on-appointment-delete-dialog.component';

import { Principal } from '../../shared';

export const serviceOnAppointmentRoute: Routes = [
    {
        path: 'service-on-appointment',
        component: ServiceOnAppointmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.serviceOnAppointment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'service-on-appointment/:id',
        component: ServiceOnAppointmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.serviceOnAppointment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const serviceOnAppointmentPopupRoute: Routes = [
    {
        path: 'service-on-appointment-new',
        component: ServiceOnAppointmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.serviceOnAppointment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'service-on-appointment/:id/edit',
        component: ServiceOnAppointmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.serviceOnAppointment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'service-on-appointment/:id/delete',
        component: ServiceOnAppointmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.serviceOnAppointment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
