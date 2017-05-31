import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RoutineCase } from './routine-case.model';
import { RoutineCaseService } from './routine-case.service';
@Injectable()
export class RoutineCasePopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private routineCaseService: RoutineCaseService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.routineCaseService.find(id).subscribe((routineCase) => {
                routineCase.beginTime = this.datePipe
                    .transform(routineCase.beginTime, 'yyyy-MM-ddThh:mm');
                routineCase.endTime = this.datePipe
                    .transform(routineCase.endTime, 'yyyy-MM-ddThh:mm');
                this.routineCaseModalRef(component, routineCase);
            });
        } else {
            return this.routineCaseModalRef(component, new RoutineCase());
        }
    }

    routineCaseModalRef(component: Component, routineCase: RoutineCase): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.routineCase = routineCase;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
