/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CautiondouaneTestModule } from '../../../test.module';
import { MarqueUpdateComponent } from 'app/entities/marque/marque-update.component';
import { MarqueService } from 'app/entities/marque/marque.service';
import { Marque } from 'app/shared/model/marque.model';

describe('Component Tests', () => {
    describe('Marque Management Update Component', () => {
        let comp: MarqueUpdateComponent;
        let fixture: ComponentFixture<MarqueUpdateComponent>;
        let service: MarqueService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CautiondouaneTestModule],
                declarations: [MarqueUpdateComponent]
            })
                .overrideTemplate(MarqueUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MarqueUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarqueService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Marque(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.marque = entity;
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
                    const entity = new Marque();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.marque = entity;
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
