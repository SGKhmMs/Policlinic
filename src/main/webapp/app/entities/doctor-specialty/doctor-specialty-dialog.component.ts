import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { DoctorSpecialty } from './doctor-specialty.model';
import { DoctorSpecialtyPopupService } from './doctor-specialty-popup.service';
import { DoctorSpecialtyService } from './doctor-specialty.service';
import { Doctor, DoctorService } from '../doctor';
import { Specialty, SpecialtyService } from '../specialty';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-doctor-specialty-dialog',
    templateUrl: './doctor-specialty-dialog.component.html'
})
export class DoctorSpecialtyDialogComponent implements OnInit {

    doctorSpecialty: DoctorSpecialty;
    authorities: any[];
    isSaving: boolean;

    doctors: Doctor[];

    specialties: Specialty[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private doctorSpecialtyService: DoctorSpecialtyService,
        private doctorService: DoctorService,
        private specialtyService: SpecialtyService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.doctorService.query()
            .subscribe((res: ResponseWrapper) => { this.doctors = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.specialtyService.query()
            .subscribe((res: ResponseWrapper) => { this.specialties = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.doctorSpecialty.id !== undefined) {
            this.subscribeToSaveResponse(
                this.doctorSpecialtyService.update(this.doctorSpecialty));
        } else {
            this.subscribeToSaveResponse(
                this.doctorSpecialtyService.create(this.doctorSpecialty));
        }
    }

    private subscribeToSaveResponse(result: Observable<DoctorSpecialty>) {
        result.subscribe((res: DoctorSpecialty) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DoctorSpecialty) {
        this.eventManager.broadcast({ name: 'doctorSpecialtyListModification', content: 'OK'});
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

    trackSpecialtyById(index: number, item: Specialty) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-doctor-specialty-popup',
    template: ''
})
export class DoctorSpecialtyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private doctorSpecialtyPopupService: DoctorSpecialtyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.doctorSpecialtyPopupService
                    .open(DoctorSpecialtyDialogComponent, params['id']);
            } else {
                this.modalRef = this.doctorSpecialtyPopupService
                    .open(DoctorSpecialtyDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
