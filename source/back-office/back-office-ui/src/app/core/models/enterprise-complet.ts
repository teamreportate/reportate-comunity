import {Enterprise} from './enterprise';
import {EconomicActivity} from './economic-activity';
import {BranchOffice} from './branch-office';

export class EnterpriseComplet {

  constructor() {
  }

  enterprise: Enterprise;

  activityEconomic: EconomicActivity[];

  branchOffice: BranchOffice;

  cuis: string;

  webService: string;

}
