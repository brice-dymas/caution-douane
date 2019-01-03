import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CautiondouaneSharedModule } from 'app/shared';
import {
    DeclarationComponent,
    DeclarationDetailComponent,
    DeclarationUpdateComponent,
    DeclarationDeletePopupComponent,
    DeclarationDeleteDialogComponent,
    declarationRoute,
    declarationPopupRoute
} from './';

const ENTITY_STATES = [...declarationRoute, ...declarationPopupRoute];

@NgModule({
    imports: [CautiondouaneSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DeclarationComponent,
        DeclarationDetailComponent,
        DeclarationUpdateComponent,
        DeclarationDeleteDialogComponent,
        DeclarationDeletePopupComponent
    ],
    entryComponents: [DeclarationComponent, DeclarationUpdateComponent, DeclarationDeleteDialogComponent, DeclarationDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CautiondouaneDeclarationModule {}
