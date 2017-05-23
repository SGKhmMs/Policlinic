import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    ServiceOnAppointmentService,
    ServiceOnAppointmentPopupService,
    ServiceOnAppointmentComponent,
    ServiceOnAppointmentDetailComponent,
    ServiceOnAppointmentDialogComponent,
    ServiceOnAppointmentPopupComponent,
    ServiceOnAppointmentDeletePopupComponent,
    ServiceOnAppointmentDeleteDialogComponent,
    serviceOnAppointmentRoute,
    serviceOnAppointmentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...serviceOnAppointmentRoute,
    ...serviceOnAppointmentPopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ServiceOnAppointmentComponent,
        ServiceOnAppointmentDetailComponent,
        ServiceOnAppointmentDialogComponent,
        ServiceOnAppointmentDeleteDialogComponent,
        ServiceOnAppointmentPopupComponent,
        ServiceOnAppointmentDeletePopupComponent,
    ],
    entryComponents: [
        ServiceOnAppointmentComponent,
        ServiceOnAppointmentDialogComponent,
        ServiceOnAppointmentPopupComponent,
        ServiceOnAppointmentDeleteDialogComponent,
        ServiceOnAppointmentDeletePopupComponent,
    ],
    providers: [
        ServiceOnAppointmentService,
        ServiceOnAppointmentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicServiceOnAppointmentModule {}
