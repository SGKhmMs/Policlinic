import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { ClinicModeratorProfile } from './clinic-moderator-profile.model';
import { ClinicModeratorProfileService } from './clinic-moderator-profile.service';

@Component({
    selector: 'jhi-clinic-moderator-profile-detail',
    templateUrl: './clinic-moderator-profile-detail.component.html'
})
export class ClinicModeratorProfileDetailComponent implements OnInit, OnDestroy {

    clinicModeratorProfile: ClinicModeratorProfile;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private clinicModeratorProfileService: ClinicModeratorProfileService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClinicModeratorProfiles();
    }

    load(id) {
        this.clinicModeratorProfileService.find(id).subscribe((clinicModeratorProfile) => {
            this.clinicModeratorProfile = clinicModeratorProfile;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClinicModeratorProfiles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'clinicModeratorProfileListModification',
            (response) => this.load(this.clinicModeratorProfile.id)
        );
    }
}
