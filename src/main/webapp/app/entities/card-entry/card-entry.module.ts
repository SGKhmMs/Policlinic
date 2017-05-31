import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    CardEntryService,
    CardEntryPopupService,
    CardEntryComponent,
    CardEntryDetailComponent,
    CardEntryDialogComponent,
    CardEntryPopupComponent,
    CardEntryDeletePopupComponent,
    CardEntryDeleteDialogComponent,
    cardEntryRoute,
    cardEntryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...cardEntryRoute,
    ...cardEntryPopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CardEntryComponent,
        CardEntryDetailComponent,
        CardEntryDialogComponent,
        CardEntryDeleteDialogComponent,
        CardEntryPopupComponent,
        CardEntryDeletePopupComponent,
    ],
    entryComponents: [
        CardEntryComponent,
        CardEntryDialogComponent,
        CardEntryPopupComponent,
        CardEntryDeleteDialogComponent,
        CardEntryDeletePopupComponent,
    ],
    providers: [
        CardEntryService,
        CardEntryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicCardEntryModule {}
