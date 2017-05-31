import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ClientAdress } from './client-adress.model';
import { ClientAdressPopupService } from './client-adress-popup.service';
import { ClientAdressService } from './client-adress.service';

@Component({
    selector: 'jhi-client-adress-delete-dialog',
    templateUrl: './client-adress-delete-dialog.component.html'
})
export class ClientAdressDeleteDialogComponent {

    clientAdress: ClientAdress;

    constructor(
        private clientAdressService: ClientAdressService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clientAdressService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'clientAdressListModification',
                content: 'Deleted an clientAdress'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-client-adress-delete-popup',
    template: ''
})
export class ClientAdressDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientAdressPopupService: ClientAdressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.clientAdressPopupService
                .open(ClientAdressDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
