import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { DoctorProfile } from './doctor-profile.model';
import { DoctorProfilePopupService } from './doctor-profile-popup.service';
import { DoctorProfileService } from './doctor-profile.service';
import { Doctor, DoctorService } from '../doctor';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-doctor-profile-dialog',
    templateUrl: './doctor-profile-dialog.component.html'
})
export class DoctorProfileDialogComponent implements OnInit {

    doctorProfile: DoctorProfile;
    authorities: any[];
    isSaving: boolean;

    doctors: Doctor[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private doctorProfileService: DoctorProfileService,
        private doctorService: DoctorService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.doctorService
            .query({filter: 'doctorprofile-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.doctorProfile.doctor || !this.doctorProfile.doctor.id) {
                    this.doctors = res.json;
                } else {
                    this.doctorService
                        .find(this.doctorProfile.doctor.id)
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
        if (this.doctorProfile.id !== undefined) {
            this.subscribeToSaveResponse(
                this.doctorProfileService.update(this.doctorProfile));
        } else {
            this.subscribeToSaveResponse(
                this.doctorProfileService.create(this.doctorProfile));
        }
    }

    private subscribeToSaveResponse(result: Observable<DoctorProfile>) {
        result.subscribe((res: DoctorProfile) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DoctorProfile) {
        this.eventManager.broadcast({ name: 'doctorProfileListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-doctor-profile-popup',
    template: ''
})
export class DoctorProfilePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private doctorProfilePopupService: DoctorProfilePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.doctorProfilePopupService
                    .open(DoctorProfileDialogComponent, params['id']);
            } else {
                this.modalRef = this.doctorProfilePopupService
                    .open(DoctorProfileDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
