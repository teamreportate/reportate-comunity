/**
 * MC4
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    26-02-19
 * author:  fmontero
 **/
import {HttpParams} from '@angular/common/http/src/params';
import {HttpHeaders} from '@angular/common/http/src/headers';


export interface HttpOptions {
  headers?: HttpHeaders | {
    [header: string]: string | string[];
  };
  observe?: 'response';
  params?: HttpParams | {
    [param: string]: string | string[];
  };
  reportProgress?: boolean;
  responseType?: 'json';
  withCredentials?: boolean;
}
