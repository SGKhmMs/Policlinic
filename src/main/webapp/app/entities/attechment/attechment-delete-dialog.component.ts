import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Attechment } from './attechment.model';
import { AttechmentPopupService } from './attechment-popup.service';
import { AttechmentService } from './attechment.service';

@Component({
    selector: 'jhi-attechment-delete-dialog',
    templateUrl: './attechment-delete-dialog.component.html'
})
export class AttechmentDeleteDialogComponent {

    attechment: Attechment;

    constructor(
        private attechmentService: AttechmentService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.attechmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'attechmentListModification',
                content: 'Deleted an attechment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-attechment-delete-popup',
    template: ''
})
export class AttechmentDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private attechmentPopupService: AttechmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.attechmentPopupService
                .open(AttechmentDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
