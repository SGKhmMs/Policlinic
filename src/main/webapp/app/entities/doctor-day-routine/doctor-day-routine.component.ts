import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { DoctorDayRoutine } from './doctor-day-routine.model';
import { DoctorDayRoutineService } from './doctor-day-routine.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-doctor-day-routine',
    templateUrl: './doctor-day-routine.component.html'
})
export class DoctorDayRoutineComponent implements OnInit, OnDestroy {
doctorDayRoutines: DoctorDayRoutine[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private doctorDayRoutineService: DoctorDayRoutineService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.doctorDayRoutineService.query().subscribe(
            (res: ResponseWrapper) => {
                this.doctorDayRoutines = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDoctorDayRoutines();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DoctorDayRoutine) {
        return item.id;
    }
    registerChangeInDoctorDayRoutines() {
        this.eventSubscriber = this.eventManager.subscribe('doctorDayRoutineListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
