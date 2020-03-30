/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    30-07-19
 * author:  fmontero
 **/

import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {HttpObserve} from '@angular/common/http/src/client';

@Injectable()
export class HttpService {
  constructor(private httpClient: HttpClient) {}

  getRequest(url: string, paramValues: {[key: string]: string} = {}, headerValues: {[key: string]: string} = {}, observe: HttpObserve | any = 'response'): Observable<any> {
    let headers: HttpHeaders = new HttpHeaders(headerValues);
    let params: HttpParams = new HttpParams({fromObject: paramValues});
    return this.httpClient.get<HttpResponse<any>>(url, {headers, observe, params});
  }

  postRequest(url: string, body: any | null, paramValues: {[key: string]: string} = {}, headerValues: {[key: string]: string} = {}, observe: HttpObserve | any = 'response'): Observable<any> {
    let headers: HttpHeaders = new HttpHeaders(headerValues);
    let params: HttpParams = new HttpParams({fromObject: paramValues});
    return this.httpClient.post<HttpResponse<any>>(url, body ? JSON.stringify(body) : null, {headers, params, observe});
  }

  putRequest(url: string, body: any | null, paramValues: {[key: string]: string} = {}, headerValues: {[key: string]: string} = {}, observe: HttpObserve | any = 'response'): Observable<any> {
    let headers: HttpHeaders = new HttpHeaders(headerValues);
    let params: HttpParams = new HttpParams({fromObject: paramValues});
    return this.httpClient.put<HttpResponse<any>>(url, body ? JSON.stringify(body) : null, {headers, params, observe});
  }

  deleteRequest(url: string, paramValues: {[key: string]: string} = {}, headerValues: {[key: string]: string} = {}, observe: HttpObserve | any = 'response'): Observable<any> {
    let headers: HttpHeaders = new HttpHeaders(headerValues);
    let params: HttpParams = new HttpParams({fromObject: paramValues});
    return this.httpClient.delete<HttpResponse<any>>(url, {headers, observe, params});
  }

  request(request: HttpRequest<any>): Observable<any> {
    return this.httpClient.request<HttpResponse<any>>(request);
  }

}
