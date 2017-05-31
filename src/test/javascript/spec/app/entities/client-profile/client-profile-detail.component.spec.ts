import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClinicTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClientProfileDetailComponent } from '../../../../../../main/webapp/app/entities/client-profile/client-profile-detail.component';
import { ClientProfileService } from '../../../../../../main/webapp/app/entities/client-profile/client-profile.service';
import { ClientProfile } from '../../../../../../main/webapp/app/entities/client-profile/client-profile.model';

describe('Component Tests', () => {

    describe('ClientProfile Management Detail Component', () => {
        let comp: ClientProfileDetailComponent;
        let fixture: ComponentFixture<ClientProfileDetailComponent>;
        let service: ClientProfileService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClinicTestModule],
                declarations: [ClientProfileDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ClientProfileService,
                    EventManager
                ]
            }).overrideComponent(ClientProfileDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClientProfileDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientProfileService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ClientProfile(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.clientProfile).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
