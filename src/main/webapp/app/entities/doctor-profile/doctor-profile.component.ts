import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { DoctorProfile } from './doctor-profile.model';
import { DoctorProfileService } from './doctor-profile.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-doctor-profile',
    templateUrl: './doctor-profile.component.html'
})
export class DoctorProfileComponent implements OnInit, OnDestroy {
doctorProfiles: DoctorProfile[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private doctorProfileService: DoctorProfileService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.doctorProfileService.query().subscribe(
            (res: ResponseWrapper) => {
                this.doctorProfiles = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDoctorProfiles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DoctorProfile) {
        return item.id;
    }
    registerChangeInDoctorProfiles() {
        this.eventSubscriber = this.eventManager.subscribe('doctorProfileListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
