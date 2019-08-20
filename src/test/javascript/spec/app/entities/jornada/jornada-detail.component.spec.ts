/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { JornadaDetailComponent } from 'app/entities/jornada/jornada-detail.component';
import { Jornada } from 'app/shared/model/jornada.model';

describe('Component Tests', () => {
  describe('Jornada Management Detail Component', () => {
    let comp: JornadaDetailComponent;
    let fixture: ComponentFixture<JornadaDetailComponent>;
    const route = ({ data: of({ jornada: new Jornada(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [JornadaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(JornadaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JornadaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jornada).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
