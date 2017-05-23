import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { ServiceOnAppointment } from './service-on-appointment.model';
import { ServiceOnAppointmentService } from './service-on-appointment.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-service-on-appointment',
    templateUrl: './service-on-appointment.component.html'
})
export class ServiceOnAppointmentComponent implements OnInit, OnDestroy {
serviceOnAppointments: ServiceOnAppointment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private serviceOnAppointmentService: ServiceOnAppointmentService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.serviceOnAppointmentService.query().subscribe(
            (res: Response) => {
                this.serviceOnAppointments = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInServiceOnAppointments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ServiceOnAppointment) {
        return item.id;
    }
    registerChangeInServiceOnAppointments() {
        this.eventSubscriber = this.eventManager.subscribe('serviceOnAppointmentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
