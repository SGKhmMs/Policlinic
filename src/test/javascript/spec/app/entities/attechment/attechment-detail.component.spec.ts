import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClinicTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AttechmentDetailComponent } from '../../../../../../main/webapp/app/entities/attechment/attechment-detail.component';
import { AttechmentService } from '../../../../../../main/webapp/app/entities/attechment/attechment.service';
import { Attechment } from '../../../../../../main/webapp/app/entities/attechment/attechment.model';

describe('Component Tests', () => {

    describe('Attechment Management Detail Component', () => {
        let comp: AttechmentDetailComponent;
        let fixture: ComponentFixture<AttechmentDetailComponent>;
        let service: AttechmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClinicTestModule],
                declarations: [AttechmentDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AttechmentService,
                    EventManager
                ]
            }).overrideComponent(AttechmentDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AttechmentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AttechmentService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Attechment(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.attechment).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
