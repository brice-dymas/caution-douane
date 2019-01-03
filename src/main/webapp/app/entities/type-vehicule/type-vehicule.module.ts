import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CautiondouaneSharedModule } from 'app/shared';
import {
    TypeVehiculeComponent,
    TypeVehiculeDetailComponent,
    TypeVehiculeUpdateComponent,
    TypeVehiculeDeletePopupComponent,
    TypeVehiculeDeleteDialogComponent,
    typeVehiculeRoute,
    typeVehiculePopupRoute
} from './';

const ENTITY_STATES = [...typeVehiculeRoute, ...typeVehiculePopupRoute];

@NgModule({
    imports: [CautiondouaneSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeVehiculeComponent,
        TypeVehiculeDetailComponent,
        TypeVehiculeUpdateComponent,
        TypeVehiculeDeleteDialogComponent,
        TypeVehiculeDeletePopupComponent
    ],
    entryComponents: [
        TypeVehiculeComponent,
        TypeVehiculeUpdateComponent,
        TypeVehiculeDeleteDialogComponent,
        TypeVehiculeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CautiondouaneTypeVehiculeModule {}
