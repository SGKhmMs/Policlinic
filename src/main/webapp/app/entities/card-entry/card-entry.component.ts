import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { CardEntry } from './card-entry.model';
import { CardEntryService } from './card-entry.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-card-entry',
    templateUrl: './card-entry.component.html'
})
export class CardEntryComponent implements OnInit, OnDestroy {
cardEntries: CardEntry[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private cardEntryService: CardEntryService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.cardEntryService.query().subscribe(
            (res: ResponseWrapper) => {
                this.cardEntries = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCardEntries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CardEntry) {
        return item.id;
    }
    registerChangeInCardEntries() {
        this.eventSubscriber = this.eventManager.subscribe('cardEntryListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
