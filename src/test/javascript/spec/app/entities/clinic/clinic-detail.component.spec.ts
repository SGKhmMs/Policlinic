import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClinicTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClinicDetailComponent } from '../../../../../../main/webapp/app/entities/clinic/clinic-detail.component';
import { ClinicService } from '../../../../../../main/webapp/app/entities/clinic/clinic.service';
import { Clinic } from '../../../../../../main/webapp/app/entities/clinic/clinic.model';

describe('Component Tests', () => {

    describe('Clinic Management Detail Component', () => {
        let comp: ClinicDetailComponent;
        let fixture: ComponentFixture<ClinicDetailComponent>;
        let service: ClinicService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClinicTestModule],
                declarations: [ClinicDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ClinicService,
                    EventManager
                ]
            }).overrideComponent(ClinicDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClinicDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClinicService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Clinic(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.clinic).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
