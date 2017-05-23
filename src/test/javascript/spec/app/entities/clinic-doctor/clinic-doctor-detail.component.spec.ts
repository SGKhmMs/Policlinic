import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClinicTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClinicDoctorDetailComponent } from '../../../../../../main/webapp/app/entities/clinic-doctor/clinic-doctor-detail.component';
import { ClinicDoctorService } from '../../../../../../main/webapp/app/entities/clinic-doctor/clinic-doctor.service';
import { ClinicDoctor } from '../../../../../../main/webapp/app/entities/clinic-doctor/clinic-doctor.model';

describe('Component Tests', () => {

    describe('ClinicDoctor Management Detail Component', () => {
        let comp: ClinicDoctorDetailComponent;
        let fixture: ComponentFixture<ClinicDoctorDetailComponent>;
        let service: ClinicDoctorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClinicTestModule],
                declarations: [ClinicDoctorDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ClinicDoctorService,
                    EventManager
                ]
            }).overrideComponent(ClinicDoctorDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClinicDoctorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClinicDoctorService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ClinicDoctor(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.clinicDoctor).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
