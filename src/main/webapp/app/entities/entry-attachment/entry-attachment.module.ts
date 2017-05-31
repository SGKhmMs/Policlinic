import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    EntryAttachmentService,
    EntryAttachmentPopupService,
    EntryAttachmentComponent,
    EntryAttachmentDetailComponent,
    EntryAttachmentDialogComponent,
    EntryAttachmentPopupComponent,
    EntryAttachmentDeletePopupComponent,
    EntryAttachmentDeleteDialogComponent,
    entryAttachmentRoute,
    entryAttachmentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...entryAttachmentRoute,
    ...entryAttachmentPopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EntryAttachmentComponent,
        EntryAttachmentDetailComponent,
        EntryAttachmentDialogComponent,
        EntryAttachmentDeleteDialogComponent,
        EntryAttachmentPopupComponent,
        EntryAttachmentDeletePopupComponent,
    ],
    entryComponents: [
        EntryAttachmentComponent,
        EntryAttachmentDialogComponent,
        EntryAttachmentPopupComponent,
        EntryAttachmentDeleteDialogComponent,
        EntryAttachmentDeletePopupComponent,
    ],
    providers: [
        EntryAttachmentService,
        EntryAttachmentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicEntryAttachmentModule {}
