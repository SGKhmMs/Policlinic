import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    DoctorAdressService,
    DoctorAdressPopupService,
    DoctorAdressComponent,
    DoctorAdressDetailComponent,
    DoctorAdressDialogComponent,
    DoctorAdressPopupComponent,
    DoctorAdressDeletePopupComponent,
    DoctorAdressDeleteDialogComponent,
    doctorAdressRoute,
    doctorAdressPopupRoute,
} from './';

const ENTITY_STATES = [
    ...doctorAdressRoute,
    ...doctorAdressPopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DoctorAdressComponent,
        DoctorAdressDetailComponent,
        DoctorAdressDialogComponent,
        DoctorAdressDeleteDialogComponent,
        DoctorAdressPopupComponent,
        DoctorAdressDeletePopupComponent,
    ],
    entryComponents: [
        DoctorAdressComponent,
        DoctorAdressDialogComponent,
        DoctorAdressPopupComponent,
        DoctorAdressDeleteDialogComponent,
        DoctorAdressDeletePopupComponent,
    ],
    providers: [
        DoctorAdressService,
        DoctorAdressPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicDoctorAdressModule {}
