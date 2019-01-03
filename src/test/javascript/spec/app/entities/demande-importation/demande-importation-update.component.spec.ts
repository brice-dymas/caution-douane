/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CautiondouaneTestModule } from '../../../test.module';
import { DemandeImportationUpdateComponent } from 'app/entities/demande-importation/demande-importation-update.component';
import { DemandeImportationService } from 'app/entities/demande-importation/demande-importation.service';
import { DemandeImportation } from 'app/shared/model/demande-importation.model';

describe('Component Tests', () => {
    describe('DemandeImportation Management Update Component', () => {
        let comp: DemandeImportationUpdateComponent;
        let fixture: ComponentFixture<DemandeImportationUpdateComponent>;
        let service: DemandeImportationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CautiondouaneTestModule],
                declarations: [DemandeImportationUpdateComponent]
            })
                .overrideTemplate(DemandeImportationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DemandeImportationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DemandeImportationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DemandeImportation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.demandeImportation = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DemandeImportation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.demandeImportation = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
