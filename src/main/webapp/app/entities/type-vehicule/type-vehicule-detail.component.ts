import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeVehicule } from 'app/shared/model/type-vehicule.model';

@Component({
    selector: 'jhi-type-vehicule-detail',
    templateUrl: './type-vehicule-detail.component.html'
})
export class TypeVehiculeDetailComponent implements OnInit {
    typeVehicule: ITypeVehicule;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeVehicule }) => {
            this.typeVehicule = typeVehicule;
        });
    }

    previousState() {
        window.history.back();
    }
}
