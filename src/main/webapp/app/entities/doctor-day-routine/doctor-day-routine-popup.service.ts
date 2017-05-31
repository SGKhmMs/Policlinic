import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { DoctorDayRoutine } from './doctor-day-routine.model';
import { DoctorDayRoutineService } from './doctor-day-routine.service';
@Injectable()
export class DoctorDayRoutinePopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private doctorDayRoutineService: DoctorDayRoutineService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.doctorDayRoutineService.find(id).subscribe((doctorDayRoutine) => {
                doctorDayRoutine.dayBeginTime = this.datePipe
                    .transform(doctorDayRoutine.dayBeginTime, 'yyyy-MM-ddThh:mm');
                doctorDayRoutine.dayEndTime = this.datePipe
                    .transform(doctorDayRoutine.dayEndTime, 'yyyy-MM-ddThh:mm');
                if (doctorDayRoutine.date) {
                    doctorDayRoutine.date = {
                        year: doctorDayRoutine.date.getFullYear(),
                        month: doctorDayRoutine.date.getMonth() + 1,
                        day: doctorDayRoutine.date.getDate()
                    };
                }
                this.doctorDayRoutineModalRef(component, doctorDayRoutine);
            });
        } else {
            return this.doctorDayRoutineModalRef(component, new DoctorDayRoutine());
        }
    }

    doctorDayRoutineModalRef(component: Component, doctorDayRoutine: DoctorDayRoutine): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.doctorDayRoutine = doctorDayRoutine;
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
