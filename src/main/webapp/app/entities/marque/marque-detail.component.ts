import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMarque } from 'app/shared/model/marque.model';

@Component({
    selector: 'jhi-marque-detail',
    templateUrl: './marque-detail.component.html'
})
export class MarqueDetailComponent implements OnInit {
    marque: IMarque;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ marque }) => {
            this.marque = marque;
        });
    }

    previousState() {
        window.history.back();
    }
}
