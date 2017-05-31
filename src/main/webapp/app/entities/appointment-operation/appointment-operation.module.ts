import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    AppointmentOperationService,
    AppointmentOperationPopupService,
    AppointmentOperationComponent,
    AppointmentOperationDetailComponent,
    AppointmentOperationDialogComponent,
    AppointmentOperationPopupComponent,
    AppointmentOperationDeletePopupComponent,
    AppointmentOperationDeleteDialogComponent,
    appointmentOperationRoute,
    appointmentOperationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...appointmentOperationRoute,
    ...appointmentOperationPopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AppointmentOperationComponent,
        AppointmentOperationDetailComponent,
        AppointmentOperationDialogComponent,
        AppointmentOperationDeleteDialogComponent,
        AppointmentOperationPopupComponent,
        AppointmentOperationDeletePopupComponent,
    ],
    entryComponents: [
        AppointmentOperationComponent,
        AppointmentOperationDialogComponent,
        AppointmentOperationPopupComponent,
        AppointmentOperationDeleteDialogComponent,
        AppointmentOperationDeletePopupComponent,
    ],
    providers: [
        AppointmentOperationService,
        AppointmentOperationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicAppointmentOperationModule {}
