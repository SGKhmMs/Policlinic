import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClinicTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MessageAttachmentDetailComponent } from '../../../../../../main/webapp/app/entities/message-attachment/message-attachment-detail.component';
import { MessageAttachmentService } from '../../../../../../main/webapp/app/entities/message-attachment/message-attachment.service';
import { MessageAttachment } from '../../../../../../main/webapp/app/entities/message-attachment/message-attachment.model';

describe('Component Tests', () => {

    describe('MessageAttachment Management Detail Component', () => {
        let comp: MessageAttachmentDetailComponent;
        let fixture: ComponentFixture<MessageAttachmentDetailComponent>;
        let service: MessageAttachmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClinicTestModule],
                declarations: [MessageAttachmentDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MessageAttachmentService,
                    EventManager
                ]
            }).overrideComponent(MessageAttachmentDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MessageAttachmentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MessageAttachmentService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MessageAttachment(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.messageAttachment).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
