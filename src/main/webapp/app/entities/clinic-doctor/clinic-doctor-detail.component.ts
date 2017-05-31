import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { ClinicDoctor } from './clinic-doctor.model';
import { ClinicDoctorService } from './clinic-doctor.service';

@Component({
    selector: 'jhi-clinic-doctor-detail',
    templateUrl: './clinic-doctor-detail.component.html'
})
export class ClinicDoctorDetailComponent implements OnInit, OnDestroy {

    clinicDoctor: ClinicDoctor;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private clinicDoctorService: ClinicDoctorService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClinicDoctors();
    }

    load(id) {
        this.clinicDoctorService.find(id).subscribe((clinicDoctor) => {
            this.clinicDoctor = clinicDoctor;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClinicDoctors() {
        this.eventSubscriber = this.eventManager.subscribe(
            'clinicDoctorListModification',
            (response) => this.load(this.clinicDoctor.id)
        );
    }
}
