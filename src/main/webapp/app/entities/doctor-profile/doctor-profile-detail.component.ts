import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { DoctorProfile } from './doctor-profile.model';
import { DoctorProfileService } from './doctor-profile.service';

@Component({
    selector: 'jhi-doctor-profile-detail',
    templateUrl: './doctor-profile-detail.component.html'
})
export class DoctorProfileDetailComponent implements OnInit, OnDestroy {

    doctorProfile: DoctorProfile;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private doctorProfileService: DoctorProfileService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDoctorProfiles();
    }

    load(id) {
        this.doctorProfileService.find(id).subscribe((doctorProfile) => {
            this.doctorProfile = doctorProfile;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDoctorProfiles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'doctorProfileListModification',
            (response) => this.load(this.doctorProfile.id)
        );
    }
}
