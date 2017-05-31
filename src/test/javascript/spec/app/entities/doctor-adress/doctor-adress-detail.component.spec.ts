import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClinicTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DoctorAdressDetailComponent } from '../../../../../../main/webapp/app/entities/doctor-adress/doctor-adress-detail.component';
import { DoctorAdressService } from '../../../../../../main/webapp/app/entities/doctor-adress/doctor-adress.service';
import { DoctorAdress } from '../../../../../../main/webapp/app/entities/doctor-adress/doctor-adress.model';

describe('Component Tests', () => {

    describe('DoctorAdress Management Detail Component', () => {
        let comp: DoctorAdressDetailComponent;
        let fixture: ComponentFixture<DoctorAdressDetailComponent>;
        let service: DoctorAdressService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClinicTestModule],
                declarations: [DoctorAdressDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DoctorAdressService,
                    EventManager
                ]
            }).overrideComponent(DoctorAdressDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DoctorAdressDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DoctorAdressService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DoctorAdress(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.doctorAdress).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
