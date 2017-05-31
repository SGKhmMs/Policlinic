import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ClinicDoctor } from './clinic-doctor.model';
import { ClinicDoctorPopupService } from './clinic-doctor-popup.service';
import { ClinicDoctorService } from './clinic-doctor.service';
import { Clinic, ClinicService } from '../clinic';
import { Doctor, DoctorService } from '../doctor';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-clinic-doctor-dialog',
    templateUrl: './clinic-doctor-dialog.component.html'
})
export class ClinicDoctorDialogComponent implements OnInit {

    clinicDoctor: ClinicDoctor;
    authorities: any[];
    isSaving: boolean;

    clinics: Clinic[];

    doctors: Doctor[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private clinicDoctorService: ClinicDoctorService,
        private clinicService: ClinicService,
        private doctorService: DoctorService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.clinicService.query()
            .subscribe((res: ResponseWrapper) => { this.clinics = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.doctorService
            .query({filter: 'clinicdoctor-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.clinicDoctor.doctor || !this.clinicDoctor.doctor.id) {
                    this.doctors = res.json;
                } else {
                    this.doctorService
                        .find(this.clinicDoctor.doctor.id)
                        .subscribe((subRes: Doctor) => {
                            this.doctors = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.clinicDoctor.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clinicDoctorService.update(this.clinicDoctor));
        } else {
            this.subscribeToSaveResponse(
                this.clinicDoctorService.create(this.clinicDoctor));
        }
    }

    private subscribeToSaveResponse(result: Observable<ClinicDoctor>) {
        result.subscribe((res: ClinicDoctor) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ClinicDoctor) {
        this.eventManager.broadcast({ name: 'clinicDoctorListModification', content: 'OK'});
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

    trackClinicById(index: number, item: Clinic) {
        return item.id;
    }

    trackDoctorById(index: number, item: Doctor) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-clinic-doctor-popup',
    template: ''
})
export class ClinicDoctorPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clinicDoctorPopupService: ClinicDoctorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.clinicDoctorPopupService
                    .open(ClinicDoctorDialogComponent, params['id']);
            } else {
                this.modalRef = this.clinicDoctorPopupService
                    .open(ClinicDoctorDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
