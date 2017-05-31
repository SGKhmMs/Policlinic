import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { CardEntry } from './card-entry.model';
import { CardEntryPopupService } from './card-entry-popup.service';
import { CardEntryService } from './card-entry.service';
import { Appointment, AppointmentService } from '../appointment';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-card-entry-dialog',
    templateUrl: './card-entry-dialog.component.html'
})
export class CardEntryDialogComponent implements OnInit {

    cardEntry: CardEntry;
    authorities: any[];
    isSaving: boolean;

    appointments: Appointment[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private cardEntryService: CardEntryService,
        private appointmentService: AppointmentService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.appointmentService.query()
            .subscribe((res: ResponseWrapper) => { this.appointments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.cardEntry.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cardEntryService.update(this.cardEntry));
        } else {
            this.subscribeToSaveResponse(
                this.cardEntryService.create(this.cardEntry));
        }
    }

    private subscribeToSaveResponse(result: Observable<CardEntry>) {
        result.subscribe((res: CardEntry) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CardEntry) {
        this.eventManager.broadcast({ name: 'cardEntryListModification', content: 'OK'});
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

    trackAppointmentById(index: number, item: Appointment) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-card-entry-popup',
    template: ''
})
export class CardEntryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cardEntryPopupService: CardEntryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.cardEntryPopupService
                    .open(CardEntryDialogComponent, params['id']);
            } else {
                this.modalRef = this.cardEntryPopupService
                    .open(CardEntryDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
