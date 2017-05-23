import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { ServiceOnAppointment } from './service-on-appointment.model';
import { ServiceOnAppointmentService } from './service-on-appointment.service';

@Component({
    selector: 'jhi-service-on-appointment-detail',
    templateUrl: './service-on-appointment-detail.component.html'
})
export class ServiceOnAppointmentDetailComponent implements OnInit, OnDestroy {

    serviceOnAppointment: ServiceOnAppointment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private serviceOnAppointmentService: ServiceOnAppointmentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInServiceOnAppointments();
    }

    load(id) {
        this.serviceOnAppointmentService.find(id).subscribe((serviceOnAppointment) => {
            this.serviceOnAppointment = serviceOnAppointment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInServiceOnAppointments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'serviceOnAppointmentListModification',
            (response) => this.load(this.serviceOnAppointment.id)
        );
    }
}
