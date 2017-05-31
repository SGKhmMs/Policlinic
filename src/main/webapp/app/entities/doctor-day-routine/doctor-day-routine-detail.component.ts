import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { DoctorDayRoutine } from './doctor-day-routine.model';
import { DoctorDayRoutineService } from './doctor-day-routine.service';

@Component({
    selector: 'jhi-doctor-day-routine-detail',
    templateUrl: './doctor-day-routine-detail.component.html'
})
export class DoctorDayRoutineDetailComponent implements OnInit, OnDestroy {

    doctorDayRoutine: DoctorDayRoutine;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private doctorDayRoutineService: DoctorDayRoutineService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDoctorDayRoutines();
    }

    load(id) {
        this.doctorDayRoutineService.find(id).subscribe((doctorDayRoutine) => {
            this.doctorDayRoutine = doctorDayRoutine;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDoctorDayRoutines() {
        this.eventSubscriber = this.eventManager.subscribe(
            'doctorDayRoutineListModification',
            (response) => this.load(this.doctorDayRoutine.id)
        );
    }
}
