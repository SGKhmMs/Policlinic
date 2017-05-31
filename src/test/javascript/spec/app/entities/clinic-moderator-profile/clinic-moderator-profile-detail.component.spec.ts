import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClinicTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClinicModeratorProfileDetailComponent } from '../../../../../../main/webapp/app/entities/clinic-moderator-profile/clinic-moderator-profile-detail.component';
import { ClinicModeratorProfileService } from '../../../../../../main/webapp/app/entities/clinic-moderator-profile/clinic-moderator-profile.service';
import { ClinicModeratorProfile } from '../../../../../../main/webapp/app/entities/clinic-moderator-profile/clinic-moderator-profile.model';

describe('Component Tests', () => {

    describe('ClinicModeratorProfile Management Detail Component', () => {
        let comp: ClinicModeratorProfileDetailComponent;
        let fixture: ComponentFixture<ClinicModeratorProfileDetailComponent>;
        let service: ClinicModeratorProfileService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClinicTestModule],
                declarations: [ClinicModeratorProfileDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ClinicModeratorProfileService,
                    EventManager
                ]
            }).overrideComponent(ClinicModeratorProfileDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClinicModeratorProfileDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClinicModeratorProfileService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ClinicModeratorProfile(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.clinicModeratorProfile).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
