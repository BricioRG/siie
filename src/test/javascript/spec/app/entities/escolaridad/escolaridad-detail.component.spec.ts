/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { EscolaridadDetailComponent } from 'app/entities/escolaridad/escolaridad-detail.component';
import { Escolaridad } from 'app/shared/model/escolaridad.model';

describe('Component Tests', () => {
  describe('Escolaridad Management Detail Component', () => {
    let comp: EscolaridadDetailComponent;
    let fixture: ComponentFixture<EscolaridadDetailComponent>;
    const route = ({ data: of({ escolaridad: new Escolaridad(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [EscolaridadDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EscolaridadDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EscolaridadDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.escolaridad).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
