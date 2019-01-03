/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CautiondouaneTestModule } from '../../../test.module';
import { RVCDeleteDialogComponent } from 'app/entities/rvc/rvc-delete-dialog.component';
import { RVCService } from 'app/entities/rvc/rvc.service';

describe('Component Tests', () => {
    describe('RVC Management Delete Component', () => {
        let comp: RVCDeleteDialogComponent;
        let fixture: ComponentFixture<RVCDeleteDialogComponent>;
        let service: RVCService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CautiondouaneTestModule],
                declarations: [RVCDeleteDialogComponent]
            })
                .overrideTemplate(RVCDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RVCDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RVCService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
