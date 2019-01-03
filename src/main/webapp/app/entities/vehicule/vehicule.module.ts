import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CautiondouaneSharedModule } from 'app/shared';
import {
    VehiculeComponent,
    VehiculeDetailComponent,
    VehiculeUpdateComponent,
    VehiculeDeletePopupComponent,
    VehiculeDeleteDialogComponent,
    vehiculeRoute,
    vehiculePopupRoute
} from './';

const ENTITY_STATES = [...vehiculeRoute, ...vehiculePopupRoute];

@NgModule({
    imports: [CautiondouaneSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VehiculeComponent,
        VehiculeDetailComponent,
        VehiculeUpdateComponent,
        VehiculeDeleteDialogComponent,
        VehiculeDeletePopupComponent
    ],
    entryComponents: [VehiculeComponent, VehiculeUpdateComponent, VehiculeDeleteDialogComponent, VehiculeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CautiondouaneVehiculeModule {}
