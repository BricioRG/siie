/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { TipoperiodoDetailComponent } from 'app/entities/tipoperiodo/tipoperiodo-detail.component';
import { Tipoperiodo } from 'app/shared/model/tipoperiodo.model';

describe('Component Tests', () => {
  describe('Tipoperiodo Management Detail Component', () => {
    let comp: TipoperiodoDetailComponent;
    let fixture: ComponentFixture<TipoperiodoDetailComponent>;
    const route = ({ data: of({ tipoperiodo: new Tipoperiodo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [TipoperiodoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoperiodoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoperiodoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoperiodo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
