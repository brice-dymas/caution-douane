import { IDemandeImportation } from 'app/shared/model//demande-importation.model';
import { ITypeVehicule } from 'app/shared/model//type-vehicule.model';
import { IMarque } from 'app/shared/model//marque.model';

export interface IDIQteTypeVehicule {
    id?: number;
    quantite?: number;
    demandeImportation?: IDemandeImportation;
    typeVehicule?: ITypeVehicule;
    marque?: IMarque;
}

export class DIQteTypeVehicule implements IDIQteTypeVehicule {
    constructor(
        public id?: number,
        public quantite?: number,
        public demandeImportation?: IDemandeImportation,
        public typeVehicule?: ITypeVehicule,
        public marque?: IMarque
    ) {}
}
