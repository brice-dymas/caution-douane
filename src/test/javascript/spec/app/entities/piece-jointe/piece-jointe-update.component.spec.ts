/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CautiondouaneTestModule } from '../../../test.module';
import { PieceJointeUpdateComponent } from 'app/entities/piece-jointe/piece-jointe-update.component';
import { PieceJointeService } from 'app/entities/piece-jointe/piece-jointe.service';
import { PieceJointe } from 'app/shared/model/piece-jointe.model';

describe('Component Tests', () => {
    describe('PieceJointe Management Update Component', () => {
        let comp: PieceJointeUpdateComponent;
        let fixture: ComponentFixture<PieceJointeUpdateComponent>;
        let service: PieceJointeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CautiondouaneTestModule],
                declarations: [PieceJointeUpdateComponent]
            })
                .overrideTemplate(PieceJointeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PieceJointeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PieceJointeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PieceJointe(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pieceJointe = entity;
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
                    const entity = new PieceJointe();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pieceJointe = entity;
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
