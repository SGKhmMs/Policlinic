import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { AppointmentOperation } from './appointment-operation.model';
import { AppointmentOperationService } from './appointment-operation.service';

@Component({
    selector: 'jhi-appointment-operation-detail',
    templateUrl: './appointment-operation-detail.component.html'
})
export class AppointmentOperationDetailComponent implements OnInit, OnDestroy {

    appointmentOperation: AppointmentOperation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private appointmentOperationService: AppointmentOperationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAppointmentOperations();
    }

    load(id) {
        this.appointmentOperationService.find(id).subscribe((appointmentOperation) => {
            this.appointmentOperation = appointmentOperation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAppointmentOperations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'appointmentOperationListModification',
            (response) => this.load(this.appointmentOperation.id)
        );
    }
}
