import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDemandeImportation } from 'app/shared/model/demande-importation.model';

@Component({
    selector: 'jhi-demande-importation-detail',
    templateUrl: './demande-importation-detail.component.html'
})
export class DemandeImportationDetailComponent implements OnInit {
    demandeImportation: IDemandeImportation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ demandeImportation }) => {
            this.demandeImportation = demandeImportation;
        });
    }

    previousState() {
        window.history.back();
    }
}
