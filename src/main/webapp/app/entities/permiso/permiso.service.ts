import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPermiso } from 'app/shared/model/permiso.model';

type EntityResponseType = HttpResponse<IPermiso>;
type EntityArrayResponseType = HttpResponse<IPermiso[]>;

@Injectable({ providedIn: 'root' })
export class PermisoService {
  public resourceUrl = SERVER_API_URL + 'api/permisos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/permisos';

  constructor(protected http: HttpClient) {}

  create(permiso: IPermiso): Observable<EntityResponseType> {
    return this.http.post<IPermiso>(this.resourceUrl, permiso, { observe: 'response' });
  }

  update(permiso: IPermiso): Observable<EntityResponseType> {
    return this.http.put<IPermiso>(this.resourceUrl, permiso, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPermiso>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPermiso[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPermiso[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
