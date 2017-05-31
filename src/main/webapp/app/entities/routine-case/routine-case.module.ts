import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    RoutineCaseService,
    RoutineCasePopupService,
    RoutineCaseComponent,
    RoutineCaseDetailComponent,
    RoutineCaseDialogComponent,
    RoutineCasePopupComponent,
    RoutineCaseDeletePopupComponent,
    RoutineCaseDeleteDialogComponent,
    routineCaseRoute,
    routineCasePopupRoute,
} from './';

const ENTITY_STATES = [
    ...routineCaseRoute,
    ...routineCasePopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RoutineCaseComponent,
        RoutineCaseDetailComponent,
        RoutineCaseDialogComponent,
        RoutineCaseDeleteDialogComponent,
        RoutineCasePopupComponent,
        RoutineCaseDeletePopupComponent,
    ],
    entryComponents: [
        RoutineCaseComponent,
        RoutineCaseDialogComponent,
        RoutineCasePopupComponent,
        RoutineCaseDeleteDialogComponent,
        RoutineCaseDeletePopupComponent,
    ],
    providers: [
        RoutineCaseService,
        RoutineCasePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicRoutineCaseModule {}
