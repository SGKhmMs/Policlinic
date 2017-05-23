import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , DataUtils } from 'ng-jhipster';

import { CardEntry } from './card-entry.model';
import { CardEntryService } from './card-entry.service';

@Component({
    selector: 'jhi-card-entry-detail',
    templateUrl: './card-entry-detail.component.html'
})
export class CardEntryDetailComponent implements OnInit, OnDestroy {

    cardEntry: CardEntry;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private dataUtils: DataUtils,
        private cardEntryService: CardEntryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCardEntries();
    }

    load(id) {
        this.cardEntryService.find(id).subscribe((cardEntry) => {
            this.cardEntry = cardEntry;
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

    registerChangeInCardEntries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cardEntryListModification',
            (response) => this.load(this.cardEntry.id)
        );
    }
}
