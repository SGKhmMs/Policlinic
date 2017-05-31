import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { Clinic } from './clinic.model';
import { ClinicService } from './clinic.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-clinic',
    templateUrl: './clinic.component.html'
})
export class ClinicComponent implements OnInit, OnDestroy {
clinics: Clinic[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private clinicService: ClinicService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.clinicService.query().subscribe(
            (res: ResponseWrapper) => {
                this.clinics = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInClinics();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Clinic) {
        return item.id;
    }
    registerChangeInClinics() {
        this.eventSubscriber = this.eventManager.subscribe('clinicListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
