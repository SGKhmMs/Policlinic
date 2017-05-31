import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { ClinicModeratorProfile } from './clinic-moderator-profile.model';
import { ClinicModeratorProfileService } from './clinic-moderator-profile.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-clinic-moderator-profile',
    templateUrl: './clinic-moderator-profile.component.html'
})
export class ClinicModeratorProfileComponent implements OnInit, OnDestroy {
clinicModeratorProfiles: ClinicModeratorProfile[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private clinicModeratorProfileService: ClinicModeratorProfileService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.clinicModeratorProfileService.query().subscribe(
            (res: ResponseWrapper) => {
                this.clinicModeratorProfiles = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInClinicModeratorProfiles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ClinicModeratorProfile) {
        return item.id;
    }
    registerChangeInClinicModeratorProfiles() {
        this.eventSubscriber = this.eventManager.subscribe('clinicModeratorProfileListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
