/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    07-08-19
 * author:  fmontero
 **/

import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Constants} from '../core/constants';
import {Constantes} from '../../environments/environment';
import {Observable} from 'rxjs';
import {AuthUser} from '../core/models/AuthUser';
import {AuthGroup} from '../core/models/auth-group';
import {AuthRole} from '../core/models/auth-role';
import {AuthResource} from '../core/models/auth-resource';

@Injectable()
export class AccessService {
  constructor(private http: HttpClient) {}

  requestUserList(): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.userApi;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestRoleList(): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.roleApi;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestUserStore(user: AuthUser, passwordConfirm: string): Observable<HttpResponse<any>> {
    const headers = new HttpHeaders({passwordConfirm});
    const url = Constantes.baseUri + Constants.userApi;
    return this.http.post<HttpResponse<any>>(url, JSON.stringify(user), {observe: 'response', headers});
  }

  requestUserUpdate(user: AuthUser, userId: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.userApi + `/${userId}`;
    return this.http.put<HttpResponse<any>>(url, JSON.stringify(user), {observe: 'response'});
  }

  requestGroupStore(group: AuthGroup): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.groupApi;
    return this.http.post<HttpResponse<any>>(url, JSON.stringify(group), {observe: 'response'});
  }

  requestGroupEdit(group: AuthGroup, groupId: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.groupApi + `/${groupId}/`;
    return this.http.put<HttpResponse<any>>(url, JSON.stringify(group), {observe: 'response'});
  }

  requestRoleStore(role: AuthRole): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.roleApi;
    return this.http.post<HttpResponse<any>>(url, JSON.stringify(role), {observe: 'response'});
  }

  requestRoleEdit(role: AuthRole, groupId: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.roleApi + `/${groupId}/`;
    return this.http.put<HttpResponse<any>>(url, JSON.stringify(role), {observe: 'response'});
  }

  requestChangeUserStatus(userId: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.userApi + `/${userId}/cambiar-estado`;
    return this.http.put<HttpResponse<any>>(url, null, {observe: 'response'});
  }

  requestChangeRoleStatus(roleId: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.roleApi + `/${roleId}/cambiar-estado`;
    return this.http.put<HttpResponse<any>>(url, null, {observe: 'response'});
  }

  requestChangeGroupStatus(groupId: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.groupApi + `/${groupId}/cambiar-estado`;
    return this.http.put<HttpResponse<any>>(url, null, {observe: 'response'});
  }

  requestUserGroups(userId: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.userApi + `/${userId}/grupos-asignados`;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestNotUserGroups(userId: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.userApi + `/${userId}/grupos-no-asignados`;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestRemoveGroupFromUser(userId: string, selectedGroupList: AuthGroup[]): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.userApi + `/${userId}/removerGrupos`;
    return this.http.post<HttpResponse<any>>(url, JSON.stringify(selectedGroupList), {observe: 'response'});
  }

  requestAddUserGroups(userId: string, selectedGroupList: AuthGroup[]): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.userApi + `/${userId}/asignarGrupos`;
    return this.http.post<HttpResponse<any>>(url, JSON.stringify(selectedGroupList), {observe: 'response'});
  }

  requestUserRoles(userId: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.userApi + `/${userId}/rolesAsignados`;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestNotUserRoles(userId: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.userApi + `/${userId}/rolesNoAsignados`;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestRemoveRolesFromUser(userId: string, selectedRoleList: AuthRole[]): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.userApi + `/${userId}/removerRoles`;
    return this.http.post<HttpResponse<any>>(url, JSON.stringify(selectedRoleList), {observe: 'response'});
  }

  requestAddUserRoles(userId: string, selectedRoleList: AuthRole[]): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.userApi + `/${userId}/asignarRoles`;
    return this.http.post<HttpResponse<any>>(url, JSON.stringify(selectedRoleList), {observe: 'response'});
  }

  requestGroupUsers(groupId: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.groupApi + `/${groupId}/usuarios-asignados`;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestNotGroupUsers(groupId: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.groupApi + `/${groupId}/usuarios-no-asignados`;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestGroupRoles(groupId: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.groupApi + `/${groupId}/roles-asignados`;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestNotGroupRoles(groupId: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.groupApi + `/${groupId}/roles-no-asignados`;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestAddGroupUsers(userId: string, userList: AuthUser[]): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.groupApi + `/${userId}/asignar-usuarios`;
    return this.http.post<HttpResponse<any>>(url, JSON.stringify(userList), {observe: 'response'});
  }

  requestRemoveUsersFromGroup(userId: string, userList: AuthUser[]): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.groupApi + `/${userId}/removerUsuarios`;
    return this.http.post<HttpResponse<any>>(url, JSON.stringify(userList), {observe: 'response'});
  }

  requestRoleResources(roleId: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.roleApi + `/${roleId}/recursos-asignados`;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestNotRoleResources(roleId: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.roleApi + `/${roleId}/recursos-no-asignados`;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestAddRoleResources(roleId: string, resourceList: AuthResource[]) {
    const url = Constantes.baseUri + Constants.roleApi + `/${roleId}/asignarRecursos`;
    return this.http.post(url, JSON.stringify(resourceList), {observe: 'response'});
  }

  requestRemoveResourcesFromRole(roleId: string, resourceList: AuthResource[]) {
    const url = Constantes.baseUri + Constants.roleApi + `/${roleId}/removerRecursos`;
    return this.http.post(url, JSON.stringify(resourceList), {observe: 'response'});
  }

  requestUserTokenList(userId: number): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.userApi + `/${userId}/lista-token`;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestChangueStateToken(tokenId: number): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.userApi + `/token/${tokenId}/inhabilitar`;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestTokenUserGenerate(userId: number, tokenData: any): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.userApi + `/${userId}/token`;
    return this.http.post<HttpResponse<any>>(url, JSON.stringify(tokenData), {observe: 'response'});
  }

  requestChangePassword(user: AuthUser, userId: string, old: string, nuevo: string): Observable<HttpResponse<any>> {
    const headers = new HttpHeaders({old: old, new: nuevo});
    const url = Constantes.baseUri + Constants.userApi + `/${userId}/cambiar-password`;
    return this.http.put<HttpResponse<any>>(url, JSON.stringify(user), {observe: 'response', headers});
  }

  requestUsernMenu(username: string) {
    const url = Constantes.baseUri + Constants.userApi + `/obtener-menu/${username}`;
    return this.http.get<any>(url, {observe: 'response'});
  }

  requestConfigureResourceRole(rolId: string, resourceConfigList: any[]): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.roleApi + `/${rolId}/configurar-recursos`;
    return this.http.post(url, JSON.stringify(resourceConfigList), {observe: 'response'});
  }

  requestConfigureUsersGroup(grupoId: string, userList: AuthUser[]): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.groupApi + `/${grupoId}/configurar-usuarios`;
    return this.http.post<HttpResponse<any>>(url, JSON.stringify(userList), {observe: 'response'});
  }

  requestDepartmentList(): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.departmentApi;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestAsignedDepartmentsList(): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.userApi + '/departamentos-asignados';
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestAsignedMunicipalitiesList(): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.userApi + '/municipios-asignados';
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestAsignedSaludCentreList(): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.userApi + '/centro-salud-asignados';
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestGetUserById(userId: number): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.userApi + '/' + userId;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }
}
