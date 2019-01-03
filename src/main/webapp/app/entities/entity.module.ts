import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CautiondouaneDemandeImportationModule } from './demande-importation/demande-importation.module';
import { CautiondouaneDIQteTypeVehiculeModule } from './di-qte-type-vehicule/di-qte-type-vehicule.module';
import { CautiondouaneMarqueModule } from './marque/marque.module';
import { CautiondouaneVehiculeModule } from './vehicule/vehicule.module';
import { CautiondouaneTypeVehiculeModule } from './type-vehicule/type-vehicule.module';
import { CautiondouaneCautionModule } from './caution/caution.module';
import { CautiondouaneDeclarationModule } from './declaration/declaration.module';
import { CautiondouaneRVCModule } from './rvc/rvc.module';
import { CautiondouaneBanqueModule } from './banque/banque.module';
import { CautiondouanePieceJointeModule } from './piece-jointe/piece-jointe.module';
import { CautiondouaneAppurementModule } from './appurement/appurement.module';
import { CautiondouaneEmployeModule } from './employe/employe.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        CautiondouaneDemandeImportationModule,
        CautiondouaneDIQteTypeVehiculeModule,
        CautiondouaneMarqueModule,
        CautiondouaneVehiculeModule,
        CautiondouaneTypeVehiculeModule,
        CautiondouaneCautionModule,
        CautiondouaneDeclarationModule,
        CautiondouaneRVCModule,
        CautiondouaneBanqueModule,
        CautiondouanePieceJointeModule,
        CautiondouaneAppurementModule,
        CautiondouaneEmployeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CautiondouaneEntityModule {}
