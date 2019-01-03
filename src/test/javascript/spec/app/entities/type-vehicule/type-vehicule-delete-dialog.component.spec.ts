/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CautiondouaneTestModule } from '../../../test.module';
import { TypeVehiculeDeleteDialogComponent } from 'app/entities/type-vehicule/type-vehicule-delete-dialog.component';
import { TypeVehiculeService } from 'app/entities/type-vehicule/type-vehicule.service';

describe('Component Tests', () => {
    describe('TypeVehicule Management Delete Component', () => {
        let comp: TypeVehiculeDeleteDialogComponent;
        let fixture: ComponentFixture<TypeVehiculeDeleteDialogComponent>;
        let service: TypeVehiculeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CautiondouaneTestModule],
                declarations: [TypeVehiculeDeleteDialogComponent]
            })
                .overrideTemplate(TypeVehiculeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeVehiculeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeVehiculeService);
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
