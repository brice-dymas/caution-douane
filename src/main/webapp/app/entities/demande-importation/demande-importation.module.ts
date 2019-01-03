import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CautiondouaneSharedModule } from 'app/shared';
import {
    DemandeImportationComponent,
    DemandeImportationDetailComponent,
    DemandeImportationUpdateComponent,
    DemandeImportationDeletePopupComponent,
    DemandeImportationDeleteDialogComponent,
    demandeImportationRoute,
    demandeImportationPopupRoute
} from './';

const ENTITY_STATES = [...demandeImportationRoute, ...demandeImportationPopupRoute];

@NgModule({
    imports: [CautiondouaneSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DemandeImportationComponent,
        DemandeImportationDetailComponent,
        DemandeImportationUpdateComponent,
        DemandeImportationDeleteDialogComponent,
        DemandeImportationDeletePopupComponent
    ],
    entryComponents: [
        DemandeImportationComponent,
        DemandeImportationUpdateComponent,
        DemandeImportationDeleteDialogComponent,
        DemandeImportationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CautiondouaneDemandeImportationModule {}
