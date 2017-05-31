import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { DoctorSpecialty } from './doctor-specialty.model';
import { DoctorSpecialtyService } from './doctor-specialty.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-doctor-specialty',
    templateUrl: './doctor-specialty.component.html'
})
export class DoctorSpecialtyComponent implements OnInit, OnDestroy {
doctorSpecialties: DoctorSpecialty[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private doctorSpecialtyService: DoctorSpecialtyService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.doctorSpecialtyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.doctorSpecialties = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDoctorSpecialties();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DoctorSpecialty) {
        return item.id;
    }
    registerChangeInDoctorSpecialties() {
        this.eventSubscriber = this.eventManager.subscribe('doctorSpecialtyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
