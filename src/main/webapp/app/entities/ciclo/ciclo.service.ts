import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICiclo } from 'app/shared/model/ciclo.model';

type EntityResponseType = HttpResponse<ICiclo>;
type EntityArrayResponseType = HttpResponse<ICiclo[]>;

@Injectable({ providedIn: 'root' })
export class CicloService {
  public resourceUrl = SERVER_API_URL + 'api/ciclos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/ciclos';

  constructor(protected http: HttpClient) {}

  create(ciclo: ICiclo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ciclo);
    return this.http
      .post<ICiclo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ciclo: ICiclo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ciclo);
    return this.http
      .put<ICiclo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICiclo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICiclo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICiclo[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(ciclo: ICiclo): ICiclo {
    const copy: ICiclo = Object.assign({}, ciclo, {
      fecini: ciclo.fecini != null && ciclo.fecini.isValid() ? ciclo.fecini.format(DATE_FORMAT) : null,
      fecfin: ciclo.fecfin != null && ciclo.fecfin.isValid() ? ciclo.fecfin.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecini = res.body.fecini != null ? moment(res.body.fecini) : null;
      res.body.fecfin = res.body.fecfin != null ? moment(res.body.fecfin) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((ciclo: ICiclo) => {
        ciclo.fecini = ciclo.fecini != null ? moment(ciclo.fecini) : null;
        ciclo.fecfin = ciclo.fecfin != null ? moment(ciclo.fecfin) : null;
      });
    }
    return res;
  }
}
