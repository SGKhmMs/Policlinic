import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ServiceOnAppointment } from './service-on-appointment.model';
import { ServiceOnAppointmentPopupService } from './service-on-appointment-popup.service';
import { ServiceOnAppointmentService } from './service-on-appointment.service';
import { Service, ServiceService } from '../service';

@Component({
    selector: 'jhi-service-on-appointment-dialog',
    templateUrl: './service-on-appointment-dialog.component.html'
})
export class ServiceOnAppointmentDialogComponent implements OnInit {

    serviceOnAppointment: ServiceOnAppointment;
    authorities: any[];
    isSaving: boolean;

    services: Service[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private serviceOnAppointmentService: ServiceOnAppointmentService,
        private serviceService: ServiceService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.serviceService.query().subscribe(
            (res: Response) => { this.services = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.serviceOnAppointment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.serviceOnAppointmentService.update(this.serviceOnAppointment));
        } else {
            this.subscribeToSaveResponse(
                this.serviceOnAppointmentService.create(this.serviceOnAppointment));
        }
    }

    private subscribeToSaveResponse(result: Observable<ServiceOnAppointment>) {
        result.subscribe((res: ServiceOnAppointment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ServiceOnAppointment) {
        this.eventManager.broadcast({ name: 'serviceOnAppointmentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackServiceById(index: number, item: Service) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-service-on-appointment-popup',
    template: ''
})
export class ServiceOnAppointmentPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private serviceOnAppointmentPopupService: ServiceOnAppointmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.serviceOnAppointmentPopupService
                    .open(ServiceOnAppointmentDialogComponent, params['id']);
            } else {
                this.modalRef = this.serviceOnAppointmentPopupService
                    .open(ServiceOnAppointmentDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
