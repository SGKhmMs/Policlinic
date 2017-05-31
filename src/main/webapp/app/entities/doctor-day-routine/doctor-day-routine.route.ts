import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { DoctorDayRoutineComponent } from './doctor-day-routine.component';
import { DoctorDayRoutineDetailComponent } from './doctor-day-routine-detail.component';
import { DoctorDayRoutinePopupComponent } from './doctor-day-routine-dialog.component';
import { DoctorDayRoutineDeletePopupComponent } from './doctor-day-routine-delete-dialog.component';

import { Principal } from '../../shared';

export const doctorDayRoutineRoute: Routes = [
    {
        path: 'doctor-day-routine',
        component: DoctorDayRoutineComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorDayRoutine.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'doctor-day-routine/:id',
        component: DoctorDayRoutineDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorDayRoutine.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const doctorDayRoutinePopupRoute: Routes = [
    {
        path: 'doctor-day-routine-new',
        component: DoctorDayRoutinePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorDayRoutine.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'doctor-day-routine/:id/edit',
        component: DoctorDayRoutinePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorDayRoutine.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'doctor-day-routine/:id/delete',
        component: DoctorDayRoutineDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorDayRoutine.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
