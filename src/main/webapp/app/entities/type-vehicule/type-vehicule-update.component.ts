import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITypeVehicule } from 'app/shared/model/type-vehicule.model';
import { TypeVehiculeService } from './type-vehicule.service';

@Component({
    selector: 'jhi-type-vehicule-update',
    templateUrl: './type-vehicule-update.component.html'
})
export class TypeVehiculeUpdateComponent implements OnInit {
    typeVehicule: ITypeVehicule;
    isSaving: boolean;

    constructor(protected typeVehiculeService: TypeVehiculeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeVehicule }) => {
            this.typeVehicule = typeVehicule;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typeVehicule.id !== undefined) {
            this.subscribeToSaveResponse(this.typeVehiculeService.update(this.typeVehicule));
        } else {
            this.subscribeToSaveResponse(this.typeVehiculeService.create(this.typeVehicule));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeVehicule>>) {
        result.subscribe((res: HttpResponse<ITypeVehicule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
