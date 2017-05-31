import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    DoctorDayRoutineService,
    DoctorDayRoutinePopupService,
    DoctorDayRoutineComponent,
    DoctorDayRoutineDetailComponent,
    DoctorDayRoutineDialogComponent,
    DoctorDayRoutinePopupComponent,
    DoctorDayRoutineDeletePopupComponent,
    DoctorDayRoutineDeleteDialogComponent,
    doctorDayRoutineRoute,
    doctorDayRoutinePopupRoute,
} from './';

const ENTITY_STATES = [
    ...doctorDayRoutineRoute,
    ...doctorDayRoutinePopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DoctorDayRoutineComponent,
        DoctorDayRoutineDetailComponent,
        DoctorDayRoutineDialogComponent,
        DoctorDayRoutineDeleteDialogComponent,
        DoctorDayRoutinePopupComponent,
        DoctorDayRoutineDeletePopupComponent,
    ],
    entryComponents: [
        DoctorDayRoutineComponent,
        DoctorDayRoutineDialogComponent,
        DoctorDayRoutinePopupComponent,
        DoctorDayRoutineDeleteDialogComponent,
        DoctorDayRoutineDeletePopupComponent,
    ],
    providers: [
        DoctorDayRoutineService,
        DoctorDayRoutinePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicDoctorDayRoutineModule {}
