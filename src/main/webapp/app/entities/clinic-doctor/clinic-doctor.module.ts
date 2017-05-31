import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    ClinicDoctorService,
    ClinicDoctorPopupService,
    ClinicDoctorComponent,
    ClinicDoctorDetailComponent,
    ClinicDoctorDialogComponent,
    ClinicDoctorPopupComponent,
    ClinicDoctorDeletePopupComponent,
    ClinicDoctorDeleteDialogComponent,
    clinicDoctorRoute,
    clinicDoctorPopupRoute,
} from './';

const ENTITY_STATES = [
    ...clinicDoctorRoute,
    ...clinicDoctorPopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClinicDoctorComponent,
        ClinicDoctorDetailComponent,
        ClinicDoctorDialogComponent,
        ClinicDoctorDeleteDialogComponent,
        ClinicDoctorPopupComponent,
        ClinicDoctorDeletePopupComponent,
    ],
    entryComponents: [
        ClinicDoctorComponent,
        ClinicDoctorDialogComponent,
        ClinicDoctorPopupComponent,
        ClinicDoctorDeleteDialogComponent,
        ClinicDoctorDeletePopupComponent,
    ],
    providers: [
        ClinicDoctorService,
        ClinicDoctorPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicClinicDoctorModule {}
