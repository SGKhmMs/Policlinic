import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { DoctorAdress } from './doctor-adress.model';
import { DoctorAdressPopupService } from './doctor-adress-popup.service';
import { DoctorAdressService } from './doctor-adress.service';
import { Doctor, DoctorService } from '../doctor';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-doctor-adress-dialog',
    templateUrl: './doctor-adress-dialog.component.html'
})
export class DoctorAdressDialogComponent implements OnInit {

    doctorAdress: DoctorAdress;
    authorities: any[];
    isSaving: boolean;

    doctors: Doctor[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private doctorAdressService: DoctorAdressService,
        private doctorService: DoctorService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.doctorService
            .query({filter: 'doctoradress-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.doctorAdress.doctor || !this.doctorAdress.doctor.id) {
                    this.doctors = res.json;
                } else {
                    this.doctorService
                        .find(this.doctorAdress.doctor.id)
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
        if (this.doctorAdress.id !== undefined) {
            this.subscribeToSaveResponse(
                this.doctorAdressService.update(this.doctorAdress));
        } else {
            this.subscribeToSaveResponse(
                this.doctorAdressService.create(this.doctorAdress));
        }
    }

    private subscribeToSaveResponse(result: Observable<DoctorAdress>) {
        result.subscribe((res: DoctorAdress) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DoctorAdress) {
        this.eventManager.broadcast({ name: 'doctorAdressListModification', content: 'OK'});
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
    selector: 'jhi-doctor-adress-popup',
    template: ''
})
export class DoctorAdressPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private doctorAdressPopupService: DoctorAdressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.doctorAdressPopupService
                    .open(DoctorAdressDialogComponent, params['id']);
            } else {
                this.modalRef = this.doctorAdressPopupService
                    .open(DoctorAdressDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
