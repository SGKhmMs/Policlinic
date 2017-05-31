import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { DoctorDayRoutine } from './doctor-day-routine.model';
import { DoctorDayRoutinePopupService } from './doctor-day-routine-popup.service';
import { DoctorDayRoutineService } from './doctor-day-routine.service';

@Component({
    selector: 'jhi-doctor-day-routine-delete-dialog',
    templateUrl: './doctor-day-routine-delete-dialog.component.html'
})
export class DoctorDayRoutineDeleteDialogComponent {

    doctorDayRoutine: DoctorDayRoutine;

    constructor(
        private doctorDayRoutineService: DoctorDayRoutineService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.doctorDayRoutineService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'doctorDayRoutineListModification',
                content: 'Deleted an doctorDayRoutine'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-doctor-day-routine-delete-popup',
    template: ''
})
export class DoctorDayRoutineDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private doctorDayRoutinePopupService: DoctorDayRoutinePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.doctorDayRoutinePopupService
                .open(DoctorDayRoutineDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
