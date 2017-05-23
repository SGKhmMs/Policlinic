import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    DoctorSpecialtyService,
    DoctorSpecialtyPopupService,
    DoctorSpecialtyComponent,
    DoctorSpecialtyDetailComponent,
    DoctorSpecialtyDialogComponent,
    DoctorSpecialtyPopupComponent,
    DoctorSpecialtyDeletePopupComponent,
    DoctorSpecialtyDeleteDialogComponent,
    doctorSpecialtyRoute,
    doctorSpecialtyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...doctorSpecialtyRoute,
    ...doctorSpecialtyPopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DoctorSpecialtyComponent,
        DoctorSpecialtyDetailComponent,
        DoctorSpecialtyDialogComponent,
        DoctorSpecialtyDeleteDialogComponent,
        DoctorSpecialtyPopupComponent,
        DoctorSpecialtyDeletePopupComponent,
    ],
    entryComponents: [
        DoctorSpecialtyComponent,
        DoctorSpecialtyDialogComponent,
        DoctorSpecialtyPopupComponent,
        DoctorSpecialtyDeleteDialogComponent,
        DoctorSpecialtyDeletePopupComponent,
    ],
    providers: [
        DoctorSpecialtyService,
        DoctorSpecialtyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicDoctorSpecialtyModule {}
