import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { CardEntry } from './card-entry.model';
import { CardEntryPopupService } from './card-entry-popup.service';
import { CardEntryService } from './card-entry.service';

@Component({
    selector: 'jhi-card-entry-delete-dialog',
    templateUrl: './card-entry-delete-dialog.component.html'
})
export class CardEntryDeleteDialogComponent {

    cardEntry: CardEntry;

    constructor(
        private cardEntryService: CardEntryService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cardEntryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cardEntryListModification',
                content: 'Deleted an cardEntry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-card-entry-delete-popup',
    template: ''
})
export class CardEntryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cardEntryPopupService: CardEntryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.cardEntryPopupService
                .open(CardEntryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
