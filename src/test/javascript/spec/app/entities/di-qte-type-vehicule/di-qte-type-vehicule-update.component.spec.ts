/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CautiondouaneTestModule } from '../../../test.module';
import { DIQteTypeVehiculeUpdateComponent } from 'app/entities/di-qte-type-vehicule/di-qte-type-vehicule-update.component';
import { DIQteTypeVehiculeService } from 'app/entities/di-qte-type-vehicule/di-qte-type-vehicule.service';
import { DIQteTypeVehicule } from 'app/shared/model/di-qte-type-vehicule.model';

describe('Component Tests', () => {
    describe('DIQteTypeVehicule Management Update Component', () => {
        let comp: DIQteTypeVehiculeUpdateComponent;
        let fixture: ComponentFixture<DIQteTypeVehiculeUpdateComponent>;
        let service: DIQteTypeVehiculeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CautiondouaneTestModule],
                declarations: [DIQteTypeVehiculeUpdateComponent]
            })
                .overrideTemplate(DIQteTypeVehiculeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DIQteTypeVehiculeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DIQteTypeVehiculeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DIQteTypeVehicule(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dIQteTypeVehicule = entity;
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
                    const entity = new DIQteTypeVehicule();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dIQteTypeVehicule = entity;
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
