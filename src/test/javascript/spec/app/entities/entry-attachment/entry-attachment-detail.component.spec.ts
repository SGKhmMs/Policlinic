import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClinicTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EntryAttachmentDetailComponent } from '../../../../../../main/webapp/app/entities/entry-attachment/entry-attachment-detail.component';
import { EntryAttachmentService } from '../../../../../../main/webapp/app/entities/entry-attachment/entry-attachment.service';
import { EntryAttachment } from '../../../../../../main/webapp/app/entities/entry-attachment/entry-attachment.model';

describe('Component Tests', () => {

    describe('EntryAttachment Management Detail Component', () => {
        let comp: EntryAttachmentDetailComponent;
        let fixture: ComponentFixture<EntryAttachmentDetailComponent>;
        let service: EntryAttachmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClinicTestModule],
                declarations: [EntryAttachmentDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EntryAttachmentService,
                    EventManager
                ]
            }).overrideComponent(EntryAttachmentDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntryAttachmentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntryAttachmentService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EntryAttachment(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.entryAttachment).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
