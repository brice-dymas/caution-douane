/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CautiondouaneTestModule } from '../../../test.module';
import { DIQteTypeVehiculeDeleteDialogComponent } from 'app/entities/di-qte-type-vehicule/di-qte-type-vehicule-delete-dialog.component';
import { DIQteTypeVehiculeService } from 'app/entities/di-qte-type-vehicule/di-qte-type-vehicule.service';

describe('Component Tests', () => {
    describe('DIQteTypeVehicule Management Delete Component', () => {
        let comp: DIQteTypeVehiculeDeleteDialogComponent;
        let fixture: ComponentFixture<DIQteTypeVehiculeDeleteDialogComponent>;
        let service: DIQteTypeVehiculeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CautiondouaneTestModule],
                declarations: [DIQteTypeVehiculeDeleteDialogComponent]
            })
                .overrideTemplate(DIQteTypeVehiculeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DIQteTypeVehiculeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DIQteTypeVehiculeService);
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
