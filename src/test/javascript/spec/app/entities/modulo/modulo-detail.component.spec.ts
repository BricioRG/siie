/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { ModuloDetailComponent } from 'app/entities/modulo/modulo-detail.component';
import { Modulo } from 'app/shared/model/modulo.model';

describe('Component Tests', () => {
  describe('Modulo Management Detail Component', () => {
    let comp: ModuloDetailComponent;
    let fixture: ComponentFixture<ModuloDetailComponent>;
    const route = ({ data: of({ modulo: new Modulo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [ModuloDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ModuloDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ModuloDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.modulo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
