import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Specialty } from './specialty.model';
import { SpecialtyPopupService } from './specialty-popup.service';
import { SpecialtyService } from './specialty.service';

@Component({
    selector: 'jhi-specialty-delete-dialog',
    templateUrl: './specialty-delete-dialog.component.html'
})
export class SpecialtyDeleteDialogComponent {

    specialty: Specialty;

    constructor(
        private specialtyService: SpecialtyService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.specialtyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'specialtyListModification',
                content: 'Deleted an specialty'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-specialty-delete-popup',
    template: ''
})
export class SpecialtyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private specialtyPopupService: SpecialtyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.specialtyPopupService
                .open(SpecialtyDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
