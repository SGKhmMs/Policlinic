import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { AppointmentOperation } from './appointment-operation.model';
import { AppointmentOperationService } from './appointment-operation.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-appointment-operation',
    templateUrl: './appointment-operation.component.html'
})
export class AppointmentOperationComponent implements OnInit, OnDestroy {
appointmentOperations: AppointmentOperation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private appointmentOperationService: AppointmentOperationService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.appointmentOperationService.query().subscribe(
            (res: ResponseWrapper) => {
                this.appointmentOperations = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAppointmentOperations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AppointmentOperation) {
        return item.id;
    }
    registerChangeInAppointmentOperations() {
        this.eventSubscriber = this.eventManager.subscribe('appointmentOperationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
