import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Appointment } from './appointment.model';
import { AppointmentPopupService } from './appointment-popup.service';
import { AppointmentService } from './appointment.service';
import { Doctor, DoctorService } from '../doctor';
import { Client, ClientService } from '../client';
import { ServiceOnAppointment, ServiceOnAppointmentService } from '../service-on-appointment';

@Component({
    selector: 'jhi-appointment-dialog',
    templateUrl: './appointment-dialog.component.html'
})
export class AppointmentDialogComponent implements OnInit {

    appointment: Appointment;
    authorities: any[];
    isSaving: boolean;

    doctors: Doctor[];

    clients: Client[];

    serviceonappointments: ServiceOnAppointment[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private appointmentService: AppointmentService,
        private doctorService: DoctorService,
        private clientService: ClientService,
        private serviceOnAppointmentService: ServiceOnAppointmentService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.doctorService.query().subscribe(
            (res: Response) => { this.doctors = res.json(); }, (res: Response) => this.onError(res.json()));
        this.clientService.query().subscribe(
            (res: Response) => { this.clients = res.json(); }, (res: Response) => this.onError(res.json()));
        this.serviceOnAppointmentService.query().subscribe(
            (res: Response) => { this.serviceonappointments = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.appointment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.appointmentService.update(this.appointment));
        } else {
            this.subscribeToSaveResponse(
                this.appointmentService.create(this.appointment));
        }
    }

    private subscribeToSaveResponse(result: Observable<Appointment>) {
        result.subscribe((res: Appointment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Appointment) {
        this.eventManager.broadcast({ name: 'appointmentListModification', content: 'OK'});
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

    trackDoctorById(index: number, item: Doctor) {
        return item.id;
    }

    trackClientById(index: number, item: Client) {
        return item.id;
    }

    trackServiceOnAppointmentById(index: number, item: ServiceOnAppointment) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-appointment-popup',
    template: ''
})
export class AppointmentPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private appointmentPopupService: AppointmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.appointmentPopupService
                    .open(AppointmentDialogComponent, params['id']);
            } else {
                this.modalRef = this.appointmentPopupService
                    .open(AppointmentDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
