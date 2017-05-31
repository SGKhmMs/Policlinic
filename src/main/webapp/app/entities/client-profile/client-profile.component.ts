import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { ClientProfile } from './client-profile.model';
import { ClientProfileService } from './client-profile.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-client-profile',
    templateUrl: './client-profile.component.html'
})
export class ClientProfileComponent implements OnInit, OnDestroy {
clientProfiles: ClientProfile[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private clientProfileService: ClientProfileService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.clientProfileService.query().subscribe(
            (res: ResponseWrapper) => {
                this.clientProfiles = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInClientProfiles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ClientProfile) {
        return item.id;
    }
    registerChangeInClientProfiles() {
        this.eventSubscriber = this.eventManager.subscribe('clientProfileListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
