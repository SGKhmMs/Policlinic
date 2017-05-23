import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { DoctorSpecialty } from './doctor-specialty.model';
import { DoctorSpecialtyService } from './doctor-specialty.service';

@Component({
    selector: 'jhi-doctor-specialty-detail',
    templateUrl: './doctor-specialty-detail.component.html'
})
export class DoctorSpecialtyDetailComponent implements OnInit, OnDestroy {

    doctorSpecialty: DoctorSpecialty;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private doctorSpecialtyService: DoctorSpecialtyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDoctorSpecialties();
    }

    load(id) {
        this.doctorSpecialtyService.find(id).subscribe((doctorSpecialty) => {
            this.doctorSpecialty = doctorSpecialty;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDoctorSpecialties() {
        this.eventSubscriber = this.eventManager.subscribe(
            'doctorSpecialtyListModification',
            (response) => this.load(this.doctorSpecialty.id)
        );
    }
}
