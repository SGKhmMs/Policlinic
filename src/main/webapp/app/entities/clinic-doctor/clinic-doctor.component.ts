import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { ClinicDoctor } from './clinic-doctor.model';
import { ClinicDoctorService } from './clinic-doctor.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-clinic-doctor',
    templateUrl: './clinic-doctor.component.html'
})
export class ClinicDoctorComponent implements OnInit, OnDestroy {
clinicDoctors: ClinicDoctor[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private clinicDoctorService: ClinicDoctorService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.clinicDoctorService.query().subscribe(
            (res: Response) => {
                this.clinicDoctors = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInClinicDoctors();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ClinicDoctor) {
        return item.id;
    }
    registerChangeInClinicDoctors() {
        this.eventSubscriber = this.eventManager.subscribe('clinicDoctorListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
