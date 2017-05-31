import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { Specialty } from './specialty.model';
import { SpecialtyService } from './specialty.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-specialty',
    templateUrl: './specialty.component.html'
})
export class SpecialtyComponent implements OnInit, OnDestroy {
specialties: Specialty[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private specialtyService: SpecialtyService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.specialtyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.specialties = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSpecialties();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Specialty) {
        return item.id;
    }
    registerChangeInSpecialties() {
        this.eventSubscriber = this.eventManager.subscribe('specialtyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
