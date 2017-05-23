import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ClinicModerator } from './clinic-moderator.model';
import { ClinicModeratorPopupService } from './clinic-moderator-popup.service';
import { ClinicModeratorService } from './clinic-moderator.service';

@Component({
    selector: 'jhi-clinic-moderator-delete-dialog',
    templateUrl: './clinic-moderator-delete-dialog.component.html'
})
export class ClinicModeratorDeleteDialogComponent {

    clinicModerator: ClinicModerator;

    constructor(
        private clinicModeratorService: ClinicModeratorService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clinicModeratorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'clinicModeratorListModification',
                content: 'Deleted an clinicModerator'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-clinic-moderator-delete-popup',
    template: ''
})
export class ClinicModeratorDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clinicModeratorPopupService: ClinicModeratorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.clinicModeratorPopupService
                .open(ClinicModeratorDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
