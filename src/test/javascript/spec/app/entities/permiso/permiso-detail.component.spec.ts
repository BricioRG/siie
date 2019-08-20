/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { PermisoDetailComponent } from 'app/entities/permiso/permiso-detail.component';
import { Permiso } from 'app/shared/model/permiso.model';

describe('Component Tests', () => {
  describe('Permiso Management Detail Component', () => {
    let comp: PermisoDetailComponent;
    let fixture: ComponentFixture<PermisoDetailComponent>;
    const route = ({ data: of({ permiso: new Permiso(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [PermisoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PermisoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PermisoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.permiso).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
