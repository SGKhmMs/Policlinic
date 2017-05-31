import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { DoctorAdress } from './doctor-adress.model';
import { DoctorAdressService } from './doctor-adress.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-doctor-adress',
    templateUrl: './doctor-adress.component.html'
})
export class DoctorAdressComponent implements OnInit, OnDestroy {
doctorAdresses: DoctorAdress[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private doctorAdressService: DoctorAdressService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.doctorAdressService.query().subscribe(
            (res: ResponseWrapper) => {
                this.doctorAdresses = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDoctorAdresses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DoctorAdress) {
        return item.id;
    }
    registerChangeInDoctorAdresses() {
        this.eventSubscriber = this.eventManager.subscribe('doctorAdressListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
