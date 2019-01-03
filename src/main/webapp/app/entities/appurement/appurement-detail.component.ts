import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAppurement } from 'app/shared/model/appurement.model';

@Component({
    selector: 'jhi-appurement-detail',
    templateUrl: './appurement-detail.component.html'
})
export class AppurementDetailComponent implements OnInit {
    appurement: IAppurement;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ appurement }) => {
            this.appurement = appurement;
        });
    }

    previousState() {
        window.history.back();
    }
}
