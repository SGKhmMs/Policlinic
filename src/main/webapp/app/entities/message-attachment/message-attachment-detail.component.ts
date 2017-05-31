import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , DataUtils } from 'ng-jhipster';

import { MessageAttachment } from './message-attachment.model';
import { MessageAttachmentService } from './message-attachment.service';

@Component({
    selector: 'jhi-message-attachment-detail',
    templateUrl: './message-attachment-detail.component.html'
})
export class MessageAttachmentDetailComponent implements OnInit, OnDestroy {

    messageAttachment: MessageAttachment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private dataUtils: DataUtils,
        private messageAttachmentService: MessageAttachmentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMessageAttachments();
    }

    load(id) {
        this.messageAttachmentService.find(id).subscribe((messageAttachment) => {
            this.messageAttachment = messageAttachment;
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

    registerChangeInMessageAttachments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'messageAttachmentListModification',
            (response) => this.load(this.messageAttachment.id)
        );
    }
}
