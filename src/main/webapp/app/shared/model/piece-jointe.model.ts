import { IDemandeImportation } from 'app/shared/model//demande-importation.model';
import { IRVC } from 'app/shared/model//rvc.model';
import { IDeclaration } from 'app/shared/model//declaration.model';

export interface IPieceJointe {
    id?: number;
    nom?: string;
    fichierContentType?: string;
    fichier?: any;
    description?: string;
    demandeImportation?: IDemandeImportation;
    rvc?: IRVC;
    declaration?: IDeclaration;
}

export class PieceJointe implements IPieceJointe {
    constructor(
        public id?: number,
        public nom?: string,
        public fichierContentType?: string,
        public fichier?: any,
        public description?: string,
        public demandeImportation?: IDemandeImportation,
        public rvc?: IRVC,
        public declaration?: IDeclaration
    ) {}
}
