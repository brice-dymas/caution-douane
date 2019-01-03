import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRVC } from 'app/shared/model/rvc.model';

@Component({
    selector: 'jhi-rvc-detail',
    templateUrl: './rvc-detail.component.html'
})
export class RVCDetailComponent implements OnInit {
    rVC: IRVC;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rVC }) => {
            this.rVC = rVC;
        });
    }

    previousState() {
        window.history.back();
    }
}
