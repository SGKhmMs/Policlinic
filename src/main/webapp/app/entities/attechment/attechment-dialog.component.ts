import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Attechment } from './attechment.model';
import { AttechmentPopupService } from './attechment-popup.service';
import { AttechmentService } from './attechment.service';
import { Massage, MassageService } from '../massage';

@Component({
    selector: 'jhi-attechment-dialog',
    templateUrl: './attechment-dialog.component.html'
})
export class AttechmentDialogComponent implements OnInit {

    attechment: Attechment;
    authorities: any[];
    isSaving: boolean;

    massages: Massage[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private attechmentService: AttechmentService,
        private massageService: MassageService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.massageService.query().subscribe(
            (res: Response) => { this.massages = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.attechment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.attechmentService.update(this.attechment));
        } else {
            this.subscribeToSaveResponse(
                this.attechmentService.create(this.attechment));
        }
    }

    private subscribeToSaveResponse(result: Observable<Attechment>) {
        result.subscribe((res: Attechment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Attechment) {
        this.eventManager.broadcast({ name: 'attechmentListModification', content: 'OK'});
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

    trackMassageById(index: number, item: Massage) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-attechment-popup',
    template: ''
})
export class AttechmentPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private attechmentPopupService: AttechmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.attechmentPopupService
                    .open(AttechmentDialogComponent, params['id']);
            } else {
                this.modalRef = this.attechmentPopupService
                    .open(AttechmentDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
