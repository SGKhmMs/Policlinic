import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { RoutineCase } from './routine-case.model';
import { RoutineCasePopupService } from './routine-case-popup.service';
import { RoutineCaseService } from './routine-case.service';

@Component({
    selector: 'jhi-routine-case-delete-dialog',
    templateUrl: './routine-case-delete-dialog.component.html'
})
export class RoutineCaseDeleteDialogComponent {

    routineCase: RoutineCase;

    constructor(
        private routineCaseService: RoutineCaseService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.routineCaseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'routineCaseListModification',
                content: 'Deleted an routineCase'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-routine-case-delete-popup',
    template: ''
})
export class RoutineCaseDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private routineCasePopupService: RoutineCasePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.routineCasePopupService
                .open(RoutineCaseDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
