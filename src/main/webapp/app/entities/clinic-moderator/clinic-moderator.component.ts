import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { ClinicModerator } from './clinic-moderator.model';
import { ClinicModeratorService } from './clinic-moderator.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-clinic-moderator',
    templateUrl: './clinic-moderator.component.html'
})
export class ClinicModeratorComponent implements OnInit, OnDestroy {
clinicModerators: ClinicModerator[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private clinicModeratorService: ClinicModeratorService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.clinicModeratorService.query().subscribe(
            (res: Response) => {
                this.clinicModerators = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInClinicModerators();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ClinicModerator) {
        return item.id;
    }
    registerChangeInClinicModerators() {
        this.eventSubscriber = this.eventManager.subscribe('clinicModeratorListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
