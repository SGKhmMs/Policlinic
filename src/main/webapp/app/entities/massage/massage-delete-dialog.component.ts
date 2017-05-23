import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Massage } from './massage.model';
import { MassagePopupService } from './massage-popup.service';
import { MassageService } from './massage.service';

@Component({
    selector: 'jhi-massage-delete-dialog',
    templateUrl: './massage-delete-dialog.component.html'
})
export class MassageDeleteDialogComponent {

    massage: Massage;

    constructor(
        private massageService: MassageService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.massageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'massageListModification',
                content: 'Deleted an massage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-massage-delete-popup',
    template: ''
})
export class MassageDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private massagePopupService: MassagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.massagePopupService
                .open(MassageDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
