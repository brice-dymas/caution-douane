import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDIQteTypeVehicule } from 'app/shared/model/di-qte-type-vehicule.model';

@Component({
    selector: 'jhi-di-qte-type-vehicule-detail',
    templateUrl: './di-qte-type-vehicule-detail.component.html'
})
export class DIQteTypeVehiculeDetailComponent implements OnInit {
    dIQteTypeVehicule: IDIQteTypeVehicule;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dIQteTypeVehicule }) => {
            this.dIQteTypeVehicule = dIQteTypeVehicule;
        });
    }

    previousState() {
        window.history.back();
    }
}
