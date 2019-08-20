import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFuncionlaboral } from 'app/shared/model/funcionlaboral.model';

type EntityResponseType = HttpResponse<IFuncionlaboral>;
type EntityArrayResponseType = HttpResponse<IFuncionlaboral[]>;

@Injectable({ providedIn: 'root' })
export class FuncionlaboralService {
  public resourceUrl = SERVER_API_URL + 'api/funcionlaborals';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/funcionlaborals';

  constructor(protected http: HttpClient) {}

  create(funcionlaboral: IFuncionlaboral): Observable<EntityResponseType> {
    return this.http.post<IFuncionlaboral>(this.resourceUrl, funcionlaboral, { observe: 'response' });
  }

  update(funcionlaboral: IFuncionlaboral): Observable<EntityResponseType> {
    return this.http.put<IFuncionlaboral>(this.resourceUrl, funcionlaboral, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFuncionlaboral>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFuncionlaboral[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFuncionlaboral[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
