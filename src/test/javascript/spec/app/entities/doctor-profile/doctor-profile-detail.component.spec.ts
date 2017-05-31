import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClinicTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DoctorProfileDetailComponent } from '../../../../../../main/webapp/app/entities/doctor-profile/doctor-profile-detail.component';
import { DoctorProfileService } from '../../../../../../main/webapp/app/entities/doctor-profile/doctor-profile.service';
import { DoctorProfile } from '../../../../../../main/webapp/app/entities/doctor-profile/doctor-profile.model';

describe('Component Tests', () => {

    describe('DoctorProfile Management Detail Component', () => {
        let comp: DoctorProfileDetailComponent;
        let fixture: ComponentFixture<DoctorProfileDetailComponent>;
        let service: DoctorProfileService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClinicTestModule],
                declarations: [DoctorProfileDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DoctorProfileService,
                    EventManager
                ]
            }).overrideComponent(DoctorProfileDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DoctorProfileDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DoctorProfileService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DoctorProfile(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.doctorProfile).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
