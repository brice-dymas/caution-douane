import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeclaration } from 'app/shared/model/declaration.model';

@Component({
    selector: 'jhi-declaration-detail',
    templateUrl: './declaration-detail.component.html'
})
export class DeclarationDetailComponent implements OnInit {
    declaration: IDeclaration;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ declaration }) => {
            this.declaration = declaration;
        });
    }

    previousState() {
        window.history.back();
    }
}
