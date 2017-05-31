import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    SpecialtyService,
    SpecialtyPopupService,
    SpecialtyComponent,
    SpecialtyDetailComponent,
    SpecialtyDialogComponent,
    SpecialtyPopupComponent,
    SpecialtyDeletePopupComponent,
    SpecialtyDeleteDialogComponent,
    specialtyRoute,
    specialtyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...specialtyRoute,
    ...specialtyPopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SpecialtyComponent,
        SpecialtyDetailComponent,
        SpecialtyDialogComponent,
        SpecialtyDeleteDialogComponent,
        SpecialtyPopupComponent,
        SpecialtyDeletePopupComponent,
    ],
    entryComponents: [
        SpecialtyComponent,
        SpecialtyDialogComponent,
        SpecialtyPopupComponent,
        SpecialtyDeleteDialogComponent,
        SpecialtyDeletePopupComponent,
    ],
    providers: [
        SpecialtyService,
        SpecialtyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicSpecialtyModule {}
