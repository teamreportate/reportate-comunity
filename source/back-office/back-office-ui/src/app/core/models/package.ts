/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    26-07-19
 * author:  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';
import {ModalityType} from '../enums/modality-type';
import {EmisionType} from '../enums/emision-type';
import {PackageState} from '../enums/package-state';
import {ParametricSin} from './parametric-sin';
import {BranchOffice} from './branch-office';
import {PointSale} from './point-sale';

export class Package extends AbstractAuditable<number> {
  idSfeSucursal: BranchOffice | null;
  idSfePuntoVenta: PointSale | null;
  tipoDocumentoFiscal: ParametricSin | null;
  tipoDocumentoSector: ParametricSin | null;
  modalidad: ModalityType | null;
  tipoEmision: EmisionType | null;
  cufd: string | null;
  cuis: string | null;
  estadoPaquete: PackageState | null;
  fechaEnvio: string | null;
  codigoRespuestaSin: number | null;
  codigoRecepcionSin: number | null;
  codigosErroresSin: string | null;
  massive: boolean | null;
  trsNitEmpresa: number | null;
  trsSucursalId: number | null;
  trsPuntoVentaId: number | null;
  trsTipoDocumentoFiscalId: number | null;
  trsTipoDocumentoSectorId: number | null;
}
