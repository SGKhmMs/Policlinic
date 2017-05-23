import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    MassageService,
    MassagePopupService,
    MassageComponent,
    MassageDetailComponent,
    MassageDialogComponent,
    MassagePopupComponent,
    MassageDeletePopupComponent,
    MassageDeleteDialogComponent,
    massageRoute,
    massagePopupRoute,
} from './';

const ENTITY_STATES = [
    ...massageRoute,
    ...massagePopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MassageComponent,
        MassageDetailComponent,
        MassageDialogComponent,
        MassageDeleteDialogComponent,
        MassagePopupComponent,
        MassageDeletePopupComponent,
    ],
    entryComponents: [
        MassageComponent,
        MassageDialogComponent,
        MassagePopupComponent,
        MassageDeleteDialogComponent,
        MassageDeletePopupComponent,
    ],
    providers: [
        MassageService,
        MassagePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicMassageModule {}
