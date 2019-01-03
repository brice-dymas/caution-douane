/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CautiondouaneTestModule } from '../../../test.module';
import { PieceJointeDeleteDialogComponent } from 'app/entities/piece-jointe/piece-jointe-delete-dialog.component';
import { PieceJointeService } from 'app/entities/piece-jointe/piece-jointe.service';

describe('Component Tests', () => {
    describe('PieceJointe Management Delete Component', () => {
        let comp: PieceJointeDeleteDialogComponent;
        let fixture: ComponentFixture<PieceJointeDeleteDialogComponent>;
        let service: PieceJointeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CautiondouaneTestModule],
                declarations: [PieceJointeDeleteDialogComponent]
            })
                .overrideTemplate(PieceJointeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PieceJointeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PieceJointeService);
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
