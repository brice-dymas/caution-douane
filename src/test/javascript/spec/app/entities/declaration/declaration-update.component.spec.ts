/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CautiondouaneTestModule } from '../../../test.module';
import { DeclarationUpdateComponent } from 'app/entities/declaration/declaration-update.component';
import { DeclarationService } from 'app/entities/declaration/declaration.service';
import { Declaration } from 'app/shared/model/declaration.model';

describe('Component Tests', () => {
    describe('Declaration Management Update Component', () => {
        let comp: DeclarationUpdateComponent;
        let fixture: ComponentFixture<DeclarationUpdateComponent>;
        let service: DeclarationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CautiondouaneTestModule],
                declarations: [DeclarationUpdateComponent]
            })
                .overrideTemplate(DeclarationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DeclarationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeclarationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Declaration(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.declaration = entity;
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
                    const entity = new Declaration();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.declaration = entity;
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
