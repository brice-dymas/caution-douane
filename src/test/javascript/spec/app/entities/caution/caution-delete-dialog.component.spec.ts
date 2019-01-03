/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CautiondouaneTestModule } from '../../../test.module';
import { CautionDeleteDialogComponent } from 'app/entities/caution/caution-delete-dialog.component';
import { CautionService } from 'app/entities/caution/caution.service';

describe('Component Tests', () => {
    describe('Caution Management Delete Component', () => {
        let comp: CautionDeleteDialogComponent;
        let fixture: ComponentFixture<CautionDeleteDialogComponent>;
        let service: CautionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CautiondouaneTestModule],
                declarations: [CautionDeleteDialogComponent]
            })
                .overrideTemplate(CautionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CautionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CautionService);
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
