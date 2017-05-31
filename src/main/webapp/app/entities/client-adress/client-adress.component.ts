import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { ClientAdress } from './client-adress.model';
import { ClientAdressService } from './client-adress.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-client-adress',
    templateUrl: './client-adress.component.html'
})
export class ClientAdressComponent implements OnInit, OnDestroy {
clientAdresses: ClientAdress[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private clientAdressService: ClientAdressService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.clientAdressService.query().subscribe(
            (res: ResponseWrapper) => {
                this.clientAdresses = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInClientAdresses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ClientAdress) {
        return item.id;
    }
    registerChangeInClientAdresses() {
        this.eventSubscriber = this.eventManager.subscribe('clientAdressListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
