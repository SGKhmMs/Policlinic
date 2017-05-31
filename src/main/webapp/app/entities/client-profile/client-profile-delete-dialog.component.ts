import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ClientProfile } from './client-profile.model';
import { ClientProfilePopupService } from './client-profile-popup.service';
import { ClientProfileService } from './client-profile.service';

@Component({
    selector: 'jhi-client-profile-delete-dialog',
    templateUrl: './client-profile-delete-dialog.component.html'
})
export class ClientProfileDeleteDialogComponent {

    clientProfile: ClientProfile;

    constructor(
        private clientProfileService: ClientProfileService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clientProfileService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'clientProfileListModification',
                content: 'Deleted an clientProfile'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-client-profile-delete-popup',
    template: ''
})
export class ClientProfileDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientProfilePopupService: ClientProfilePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.clientProfilePopupService
                .open(ClientProfileDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
