import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { ClientProfile } from './client-profile.model';
import { ClientProfileService } from './client-profile.service';

@Component({
    selector: 'jhi-client-profile-detail',
    templateUrl: './client-profile-detail.component.html'
})
export class ClientProfileDetailComponent implements OnInit, OnDestroy {

    clientProfile: ClientProfile;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private clientProfileService: ClientProfileService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClientProfiles();
    }

    load(id) {
        this.clientProfileService.find(id).subscribe((clientProfile) => {
            this.clientProfile = clientProfile;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClientProfiles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'clientProfileListModification',
            (response) => this.load(this.clientProfile.id)
        );
    }
}
