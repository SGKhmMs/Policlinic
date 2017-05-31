import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , DataUtils } from 'ng-jhipster';

import { EntryAttachment } from './entry-attachment.model';
import { EntryAttachmentService } from './entry-attachment.service';

@Component({
    selector: 'jhi-entry-attachment-detail',
    templateUrl: './entry-attachment-detail.component.html'
})
export class EntryAttachmentDetailComponent implements OnInit, OnDestroy {

    entryAttachment: EntryAttachment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private dataUtils: DataUtils,
        private entryAttachmentService: EntryAttachmentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEntryAttachments();
    }

    load(id) {
        this.entryAttachmentService.find(id).subscribe((entryAttachment) => {
            this.entryAttachment = entryAttachment;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEntryAttachments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'entryAttachmentListModification',
            (response) => this.load(this.entryAttachment.id)
        );
    }
}
